package cn.lzheng.rpc.transport;

import cn.lzheng.rpc.annotation.RpcServiceScans;
import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.provider.ServiceProvider;
import cn.lzheng.rpc.registry.ServiceRegistry;
import cn.lzheng.rpc.utils.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void scanServices() {
        String mainClassName = ReflectUtil.getMainClassName();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);

            if(!startClass.isAnnotationPresent(RpcServiceScans.class)){
                logger.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }

        }catch (ClassNotFoundException e){
            logger.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
    }


}
