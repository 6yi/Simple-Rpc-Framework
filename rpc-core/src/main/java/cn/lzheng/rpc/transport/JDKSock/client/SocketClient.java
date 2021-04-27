package cn.lzheng.rpc.transport.JDKSock.client;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.loadbalancer.LoadBalancer;
import cn.lzheng.rpc.loadbalancer.RandomLoadBalancer;
import cn.lzheng.rpc.registry.Redis.RedisDiscovery;
import cn.lzheng.rpc.registry.ServiceDiscovery;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static cn.lzheng.rpc.serializer.CommonSerializer.DEFAULT_SERIALIZER;


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

    public SocketClient() {
        this(DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }
    public SocketClient(LoadBalancer loadBalancer) {
        this(DEFAULT_SERIALIZER, loadBalancer);
    }
    public SocketClient(Integer serializer) {
        this(serializer, new RandomLoadBalancer());
    }

    public SocketClient(Integer serializer, LoadBalancer loadBalancer) {
        this.serviceDiscovery = new RedisDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {


        return null;
    }

}
