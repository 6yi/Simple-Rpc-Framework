package cn.lzheng.rpc.transport;

import cn.lzheng.rpc.registry.ServiceDiscovery;

/**
 * @ClassName AbstractRpcClient
 * @Author 6yi
 * @Date 2021/5/2 22:07
 * @Version 1.0
 * @Description:
 */


public abstract class AbstractRpcClient implements RpcClient{

    protected ServiceDiscovery serviceDiscovery;



}
