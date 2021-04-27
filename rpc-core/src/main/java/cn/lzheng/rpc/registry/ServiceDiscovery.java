package cn.lzheng.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @ClassName ServiceDiscovery
 * @Author 6yi
 * @Date 2021/4/26 20:16
 * @Version 1.0
 * @Description:
 */

public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务实体
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);


}
