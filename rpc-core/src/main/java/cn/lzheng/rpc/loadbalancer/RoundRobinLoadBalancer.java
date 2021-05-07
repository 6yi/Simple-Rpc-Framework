package cn.lzheng.rpc.loadbalancer;

import cn.lzheng.rpc.entity.RpcInstance;

import java.util.List;

/**
 * @ClassName RoundRobinLoadBalancer
 * @Author 6yi
 * @Date 2021/5/7 12:58
 * @Version 1.0
 * @Description:
 */


public class RoundRobinLoadBalancer implements LoadBalancer {

    int index=0;

    @Override
    public RpcInstance select(List<RpcInstance> instances) {
        if(index >= instances.size()) {
            index %= instances.size();
        }
        return instances.get(index++);
    }

}
