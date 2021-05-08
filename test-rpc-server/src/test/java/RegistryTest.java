import cn.lzheng.rpc.annotation.RpcServiceScans;
import cn.lzheng.rpc.enumeration.RegistryCode;

import cn.lzheng.rpc.transport.JDKSocket.server.SocketServer;
import cn.lzheng.rpc.transport.Netty.server.NettyServer;
import org.junit.Test;
import java.net.UnknownHostException;
import java.util.List;


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
//        NettyServer nettyServer = new NettyServer("127.0.0.1", RegistryCode.NACOS.getCode(), "127.0.0.1:8848");

//        nettyServer.start();
        new SocketServer("127.0.0.1",RegistryCode.NACOS.getCode(),"127.0.0.1:8848").start();

    }

    public static <T> List<T> fnc(T t){
        return null;
    }

    @Test
    public void test() throws UnknownHostException {

//        RedisRegistry redisRegistry = new RedisRegistry();
//        redisRegistry.registry("lzhengRpcTest", new InetSocketAddress("127.0.0.2",8088));
//        InetSocketAddress rpcTest = new RedisDiscovery(new RandomLoadBalancer()).lookupService("lzhengRpcTest");


    }



}
