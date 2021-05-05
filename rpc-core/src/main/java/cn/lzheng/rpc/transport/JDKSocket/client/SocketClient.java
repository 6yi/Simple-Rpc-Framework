package cn.lzheng.rpc.transport.JDKSocket.client;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.enumeration.ResponseCode;
import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.loadbalancer.LoadBalancer;
import cn.lzheng.rpc.loadbalancer.RandomLoadBalancer;
import cn.lzheng.rpc.registry.ServiceDiscovery;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.JDKSocket.utils.RpcObjectReader;
import cn.lzheng.rpc.transport.JDKSocket.utils.RpcObjectWriter;
import cn.lzheng.rpc.transport.RpcClient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * @ClassName SocketClient
 * @Author 6yi
 * @Date 2021/4/26 1:14
 * @Version 1.0
 * @Description:  使用JDKsocket方式进行远程调用
 */
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final ServiceDiscovery serviceDiscovery;

    private final CommonSerializer serializer;


    public SocketClient(){
        this(null,DEFAULT_REGISTRY,DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }

    public SocketClient(String URI,Integer discovery){
        this(URI,discovery,DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }

    public SocketClient(LoadBalancer loadBalancer) {
        this(null,DEFAULT_REGISTRY,DEFAULT_SERIALIZER, loadBalancer);
    }


    public SocketClient(String URI,Integer discovery,Integer serializer) {
        this(URI,discovery,serializer, new RandomLoadBalancer());
    }

    public SocketClient(String URI,Integer discovery,Integer serializer, LoadBalancer loadBalancer) {
        this.serviceDiscovery = ServiceDiscovery.getByCode(URI,discovery,loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null){
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try(Socket socket = new Socket()){
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            RpcObjectWriter.writeObject(outputStream,rpcRequest,serializer);

            RpcResponse rpcResponse = (RpcResponse) RpcObjectReader.readObject(inputStream);
            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse;
        }catch (Exception e){
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

}
