package cn.lzheng.rpc.transport;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.registry.ServiceRegistry;
import cn.lzheng.rpc.serializer.CommonSerializer;

/**
 * @ClassName RpcClient
 * @Author 6yi
 * @Date 2021/4/26 1:11
 * @Version 1.0
 * @Description:  Rpc通用接口，可以根据不同的网络框架去实现传输
 */

public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;
    int DEFAULT_REGISTRY = ServiceRegistry.DEFAULT_REGISTRY;

    Object sendRequest(RpcRequest rpcRequest);

}
