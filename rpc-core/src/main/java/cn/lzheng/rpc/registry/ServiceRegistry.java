package cn.lzheng.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @ClassName ServiceRegistry
 * @Author 6yi
 * @Date 2021/4/26 15:02
 * @Version 1.0
 * @Description: 服务注册接口
 */

public interface ServiceRegistry {

    /**
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     **/
    void registry(String serviceName, InetSocketAddress inetSocketAddress);

}
