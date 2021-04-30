package cn.lzheng.rpc.transport;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.JDKSocket.client.SocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @ClassName RpcClientProxy
 * @Author 6yi
 * @Date 2021/5/1 0:29
 * @Version 1.0
 * @Description: 动态代理RPC客户端
 */


public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);
    private final RpcClient rpcClient;

    public RpcClientProxy(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId(UUID.randomUUID().toString())
                .method(method.getName())
                .argsType(method.getParameterTypes())
                .args(args)
                .interfaceName(method.getDeclaringClass().getName()).build();
        RpcResponse rpcResponse = null;
        if(rpcClient instanceof SocketClient){
            rpcResponse = (RpcResponse) rpcClient.sendRequest(rpcRequest);
        }
        return rpcResponse.getData();
    }

}
