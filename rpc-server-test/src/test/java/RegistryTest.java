import cn.lzheng.rpc.annotation.RpcServiceScans;
import cn.lzheng.rpc.loadbalancer.RandomLoadBalancer;
import cn.lzheng.rpc.registry.Redis.RedisDiscovery;
import cn.lzheng.rpc.registry.Redis.RedisRegistry;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.JDKSocket.server.SocketServer;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @ClassName RegistryTest
 * @Author 6yi
 * @Date 2021/4/27 23:34
 * @Version 1.0
 * @Description:
 */

@RpcServiceScans("cn.lzheng.test")
public class RegistryTest {

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer("192.168.123.17","192.168.123.17:8848");
        socketServer.start();
    }

    @Test
    public void test() throws UnknownHostException {

//        RedisRegistry redisRegistry = new RedisRegistry();
//        redisRegistry.registry("lzhengRpcTest", new InetSocketAddress("127.0.0.2",8088));
//        InetSocketAddress rpcTest = new RedisDiscovery(new RandomLoadBalancer()).lookupService("lzhengRpcTest");


    }



}
