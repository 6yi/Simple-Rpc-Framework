package cn.lzheng.rpc.handler;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.enumeration.ResponseCode;
import cn.lzheng.rpc.provider.ServiceProvider;
import cn.lzheng.rpc.provider.ServiceProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName InvokeMethodHandler
 * @Author 6yi
 * @Date 2021/4/26 14:58
 * @Version 1.0
 * @Description: 过程调用通用处理器
 */


public class InvokeMethodHandler {

    private static final Logger logger = LoggerFactory.getLogger(InvokeMethodHandler.class);
    private static final ServiceProvider serviceProvider;

    static {
        serviceProvider = new ServiceProviderImpl();
    }

    public Object handle(RpcRequest rpcRequest){
        Object service = InvokeMethodHandler.serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        return invokeTargetMethod(rpcRequest,service);
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest,Object service){
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethod(), rpcRequest.getArgsType());
            result = method.invoke(service,rpcRequest.getArgs());
            logger.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethod());
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        return result;
    }

}
