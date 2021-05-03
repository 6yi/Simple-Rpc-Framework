package cn.lzheng.rpc.transport;

import cn.lzheng.rpc.annotation.RpcService;
import cn.lzheng.rpc.annotation.RpcServiceScans;
import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.provider.ServiceProvider;
import cn.lzheng.rpc.registry.ServiceRegistry;
import cn.lzheng.rpc.utils.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @ClassName AbstractRpcServer
 * @Author 6yi
 * @Date 2021/4/28 0:42
 * @Version 1.0
 * @Description:
 */

public abstract class AbstractRpcServer implements RpcServer{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    /**
     * @author 6yi
     * @date 2021/4/28
     * @return
     * @Description  扫描服务
     **/
    protected void scanServices() {
        String mainClassName = ReflectUtil.getMainClassName();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if(!startClass.isAnnotationPresent(RpcServiceScans.class)){
                logger.error("启动类缺少 @RpcServiceScans 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
            String basePackage = startClass.getAnnotation(RpcServiceScans.class).value();
            if("".equals(basePackage)){
                //默认扫描主类下
                basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
            }
            Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);

            for (Class<?> clazz:classSet){
                if(clazz.isAnnotationPresent(RpcService.class)){
                    String serviceName = clazz.getAnnotation(RpcService.class).name();
                    Object obj;
                    try{
                        obj = clazz.newInstance();
                    } catch (IllegalAccessException | InstantiationException e) {
                        logger.error("创建 " + clazz + " 时有错误发生");
                        continue;
                    }
                    if("".equals(serviceName)){
                        Class<?>[] interfaces = clazz.getInterfaces();
                        for (Class<?> oneInterface: interfaces){
                            publishService(obj, oneInterface.getCanonicalName());
                        }
                    }else{
                        publishService(obj, serviceName);
                    }
                }
            }

        }catch (ClassNotFoundException e){
            logger.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
    }


    //注册服务
    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceRegistry.registry(serviceName, new InetSocketAddress(host, port));
        serviceProvider.addServiceProvider(service,serviceName);
    }
}
