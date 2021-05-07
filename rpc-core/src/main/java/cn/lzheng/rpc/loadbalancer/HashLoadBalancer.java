package cn.lzheng.rpc.loadbalancer;

import cn.lzheng.rpc.entity.RpcInstance;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName HashLoadBalancer
 * @Author 6yi
 * @Date 2021/5/7 13:06
 * @Version 1.0
 * @Description: hash均衡负载
 */


public class HashLoadBalancer implements LoadBalancer{

    private static final int hashcode;

    static {
        int uuid = UUID.randomUUID().hashCode();
        hashcode = (uuid>>16) & uuid;
    }

    @Override
    public RpcInstance select(List<RpcInstance> instances) {
        int index = hashcode & (instances.size()-1);
        return instances.get(index);
    }

}
