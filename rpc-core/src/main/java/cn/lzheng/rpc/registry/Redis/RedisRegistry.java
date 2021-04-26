package cn.lzheng.rpc.registry.Redis;

import cn.lzheng.rpc.registry.ServiceRegistry;
import cn.lzheng.rpc.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @ClassName RedisRegistry
 * @Author 6yi
 * @Date 2021/4/26 15:06
 * @Version 1.0
 * @Description:
 */


public class RedisRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(RedisRegistry.class);

    @Override
    public void registry(String serviceName, InetSocketAddress inetSocketAddress) {
        try {

        } catch (Exception e) {
            logger.error("注册服务时有错误发生:", e);

        }
    }
}
