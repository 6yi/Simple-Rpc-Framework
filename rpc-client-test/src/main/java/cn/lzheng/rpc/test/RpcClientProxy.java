package cn.lzheng.rpc.test;

import cn.lzheng.rpc.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName RpcClientProxy
 * @Author 6yi
 * @Date 2021/4/26 1:00
 * @Version 1.0
 * @Description:
 */


public class RpcClientProxy implements InvocationHandler {

    private String host;
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest rpcRequest = RpcRequest
                .builder()
                .args(args)
                .method(method.getName())
                .argsType(method.getParameterTypes())
                .interfaceName(method.getDeclaringClass().getName()).build();
        
        return null;
    }
}
