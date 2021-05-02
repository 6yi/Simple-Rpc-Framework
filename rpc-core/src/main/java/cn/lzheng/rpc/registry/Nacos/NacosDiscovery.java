package cn.lzheng.rpc.registry.Nacos;

import cn.lzheng.rpc.entity.RpcInstance;
import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.loadbalancer.LoadBalancer;
import cn.lzheng.rpc.loadbalancer.RandomLoadBalancer;
import cn.lzheng.rpc.registry.ServiceDiscovery;
import cn.lzheng.rpc.utils.NacosUtil;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName NacosDiscovery
 * @Author 6yi
 * @Date 2021/5/2 16:23
 * @Version 1.0
 * @Description:
 */


public class NacosDiscovery implements ServiceDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(NacosDiscovery.class);

    private final LoadBalancer loadBalancer;

    public NacosDiscovery(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<RpcInstance> instances = NacosUtil.getAllInstance(serviceName).stream().map(h->
                RpcInstance.builder().host(h.getIp()).port(h.getPort()).health(h.isHealthy()).weight(h.getWeight()).build()
            ).collect(Collectors.toList());
            if(instances.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            RpcInstance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getHost(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
