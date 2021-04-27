package cn.lzheng.rpc.loadbalancer;

import cn.lzheng.rpc.entity.RpcInstance;
import java.util.List;
import java.util.Random;

/*
*
* 随机均衡选择器
* */
public class RandomLoadBalancer implements LoadBalancer {


    @Override
    public RpcInstance select(List<RpcInstance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }

}
