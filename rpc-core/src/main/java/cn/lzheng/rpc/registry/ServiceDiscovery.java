package cn.lzheng.rpc.registry;

import cn.lzheng.rpc.loadbalancer.LoadBalancer;
import cn.lzheng.rpc.registry.Nacos.NacosDiscovery;
import cn.lzheng.rpc.registry.Nacos.NacosRegistry;
import cn.lzheng.rpc.registry.Redis.RedisDiscovery;
import cn.lzheng.rpc.registry.Redis.RedisRegistry;

import java.net.InetSocketAddress;

/**
 * @ClassName ServiceDiscovery
 * @Author 6yi
 * @Date 2021/4/26 20:16
 * @Version 1.0
 * @Description:
 */

public interface ServiceDiscovery {

    Integer REDIS_DISCOVERY = 0;
    Integer NACOS_DISCOVERY = 1;
    Integer DEFAULT_DISCOVERY = NACOS_DISCOVERY;

    /**
     * 根据服务名称查找服务实体
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);

    static ServiceDiscovery getByCode(int code, LoadBalancer loadBalancer){
        switch (code){
            case 0:
                return new RedisDiscovery(loadBalancer);
            case 1:
                return new NacosDiscovery(loadBalancer);
            default:
                return null;
        }
    }

}
