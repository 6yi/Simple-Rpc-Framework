package cn.lzheng.rpc.transport.Netty.server;


import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.factory.ThreadPoolFactory;
import cn.lzheng.rpc.handler.InvokeMethodHandler;
import cn.lzheng.rpc.provider.ServiceProviderImpl;
import cn.lzheng.rpc.registry.ServiceRegistry;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.AbstractRpcServer;
import cn.lzheng.rpc.transport.Netty.codec.CommonDecoder;
import cn.lzheng.rpc.transport.Netty.codec.CommonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyServer
 * @Author 6yi
 * @Date 2021/5/5 15:48
 * @Version 1.0
 * @Description:
 */


public class NettyServer extends AbstractRpcServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final InvokeMethodHandler requestHandler = new InvokeMethodHandler();

    public NettyServer(String host,String URI){
        this(host,null,CommonSerializer.DEFAULT_SERIALIZER,ServiceRegistry.DEFAULT_REGISTRY,URI);
    }

    public NettyServer(String host,Integer registry,String URI){
        this(host,null,CommonSerializer.DEFAULT_SERIALIZER,registry,URI);
    }

    public NettyServer(String host,Integer port,Integer serializer,Integer registry,String URI){
        this.host = host;
        if (port==null){
            try {
                ServerSocket serverSocket = new ServerSocket(0);
                this.port = serverSocket.getLocalPort();
                serverSocket.close();
                logger.info("随机端口号:{}",this.port);
            } catch (Exception e) {
                logger.error("无空闲端口号");
                throw new RpcException(RpcError.UNKNOWN_ERROR);
            }
        }else{
            this.port = port;
        }
        this.serviceRegistry = ServiceRegistry.getByCode(URI,registry);
        this.serializer = CommonSerializer.getByCode(serializer);
        this.serviceProvider=new ServiceProviderImpl();
        scanServices();
    }


    @Override
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serviceRegistry.clearRegistry();
            ThreadPoolFactory.shutDownAll();
        }));
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new IdleStateHandler(30,0,0, TimeUnit.SECONDS))
                                    .addLast(new CommonDecoder())
                                    .addLast(new CommonEncoder(serializer))
                                    .addLast(new NettyRequestHandler(requestHandler));
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            logger.info("rpc服务启动成功");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error("启动失败:"+e);
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }


}
