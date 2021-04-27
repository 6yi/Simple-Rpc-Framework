package cn.lzheng.rpc.loadbalancer;

import cn.lzheng.rpc.entity.RpcInstance;

import java.util.List;

/**
 * @ClassName LoadBalancer
 * @Author 6yi
 * @Date 2021/4/26 20:30
 * @Version 1.0
 * @Description:
 */

public interface LoadBalancer {

    RpcInstance select(List<RpcInstance> instances);

}
