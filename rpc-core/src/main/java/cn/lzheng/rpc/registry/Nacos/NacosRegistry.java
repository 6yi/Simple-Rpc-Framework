package cn.lzheng.rpc.registry.Nacos;

import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.registry.ServiceRegistry;
import cn.lzheng.rpc.utils.NacosUtil;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @ClassName NacosRegistry
 * @Author 6yi
 * @Date 2021/5/2 14:56
 * @Version 1.0
 * @Description:
 */


public class NacosRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(NacosRegistry.class);

    @Override
    public void registry(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName,inetSocketAddress);
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
