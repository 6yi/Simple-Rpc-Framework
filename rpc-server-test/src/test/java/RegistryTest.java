import cn.lzheng.rpc.loadbalancer.RandomLoadBalancer;
import cn.lzheng.rpc.registry.Redis.RedisDiscovery;
import cn.lzheng.rpc.registry.Redis.RedisRegistry;
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


public class RegistryTest {

    @Test
    public void test() throws UnknownHostException {

        RedisRegistry redisRegistry = new RedisRegistry();
        redisRegistry.registry("lzhengRpcTest", new InetSocketAddress("127.0.0.2",8088));
        InetSocketAddress rpcTest = new RedisDiscovery(new RandomLoadBalancer()).lookupService("lzhengRpcTest");

    }


}
