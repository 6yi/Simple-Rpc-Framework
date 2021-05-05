import cn.lzheng.rpc.annotation.RpcServiceScans;
import cn.lzheng.rpc.enumeration.RegistryCode;
import cn.lzheng.rpc.transport.JDKSocket.server.SocketServer;
import cn.lzheng.rpc.transport.Netty.server.NettyServer;
import org.junit.Test;
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
        NettyServer nettyServer = new NettyServer("192.168.123.17", RegistryCode.NACOS.getCode(), "192.168.123.17:8848");
        nettyServer.start();
//        SocketServer socketServer = new SocketServer("192.168.123.17","192.168.123.17:8848");
//        socketServer.start();
    }

    @Test
    public void test() throws UnknownHostException {

//        RedisRegistry redisRegistry = new RedisRegistry();
//        redisRegistry.registry("lzhengRpcTest", new InetSocketAddress("127.0.0.2",8088));
//        InetSocketAddress rpcTest = new RedisDiscovery(new RandomLoadBalancer()).lookupService("lzhengRpcTest");


    }



}
