package cn.lzheng.rpc.transport.Netty.client;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.loadbalancer.LoadBalancer;
import cn.lzheng.rpc.loadbalancer.RandomLoadBalancer;
import cn.lzheng.rpc.registry.ServiceDiscovery;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName NettyClient
 * @Author 6yi
 * @Date 2021/5/5 21:19
 * @Version 1.0
 * @Description:
 */


public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

//    private static final EventLoopGroup workGroup;
//    private static final Bootstrap bootStrap;

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;
    private final CacheRequest cacheRequest;

//    static {
//        workGroup = new NioEventLoopGroup();
//        bootStrap = new Bootstrap();
//        bootStrap.group(workGroup).channel(NioSocketChannel.class);
//    }

    public NettyClient(){
        this(null,DEFAULT_REGISTRY,DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }

    public NettyClient(String URI,Integer discovery){this(URI,discovery,DEFAULT_SERIALIZER, new RandomLoadBalancer()); }

    public NettyClient(LoadBalancer loadBalancer) {
        this(null,DEFAULT_REGISTRY,DEFAULT_SERIALIZER, loadBalancer);
    }

    public NettyClient(String URI,Integer discovery,Integer serializer) {
        this(URI,discovery,serializer, new RandomLoadBalancer());
    }

    public NettyClient(String URI, Integer discovery, Integer serializer, LoadBalancer loadBalancer){
        this.serviceDiscovery = ServiceDiscovery.getByCode(URI,discovery,loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
        this.cacheRequest=CacheRequest.getCacheRequest();
    }

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
        logger.info("start");
        if (serializer == null){
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        CompletableFuture<RpcResponse> resultFuture = new CompletableFuture<>();
        try{
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress,serializer);
            if(!channel.isActive()){
//                workGroup.shutdownGracefully();
                return null;
            }
            cacheRequest.add(rpcRequest.getRequestId(),resultFuture);
            channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener)future->{
                if(future.isSuccess()){
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                }else{
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    logger.error("发送消息时有错误发生: ", future.cause());
                }
            });
        } catch (Exception e){
            cacheRequest.remove(rpcRequest.getRequestId());
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return resultFuture;
    }

}
