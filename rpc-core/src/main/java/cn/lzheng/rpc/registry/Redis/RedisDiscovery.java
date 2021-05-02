package cn.lzheng.rpc.registry.Redis;

import cn.lzheng.rpc.entity.RpcInstance;
import cn.lzheng.rpc.loadbalancer.LoadBalancer;
import cn.lzheng.rpc.loadbalancer.RandomLoadBalancer;
import cn.lzheng.rpc.registry.ServiceDiscovery;
import cn.lzheng.rpc.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RedisDiscovery
 * @Author 6yi
 * @Date 2021/4/26 20:19
 * @Version 1.0
 * @Description: redis发现中心
 */


public class RedisDiscovery implements ServiceDiscovery {

    private final LoadBalancer loadBalancer;

    private static final Logger logger = LoggerFactory.getLogger(RedisDiscovery.class);

    public RedisDiscovery(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try{
            List<RpcInstance> instances = RedisUtil
                    .getAllInstance(serviceName)
                    .stream().map(h->h.toInstance())
                    .collect(Collectors.toList());
            if(instances.size()==0){
                logger.error("找不到对应的服务: " + serviceName);
            }
            RpcInstance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getHost(),instance.getPort());
        }catch (Exception e){
            logger.error("获取服务时有错误发生:", e);
        }
        return null;
    }

}
