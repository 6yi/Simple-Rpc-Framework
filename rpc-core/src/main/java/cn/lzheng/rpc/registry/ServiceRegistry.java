package cn.lzheng.rpc.registry;

import cn.lzheng.rpc.registry.Nacos.NacosRegistry;
import cn.lzheng.rpc.registry.Redis.RedisRegistry;
import cn.lzheng.rpc.utils.NacosUtil;
import cn.lzheng.rpc.utils.RedisUtil;

import java.net.InetSocketAddress;

/**
 * @ClassName ServiceRegistry
 * @Author 6yi
 * @Date 2021/4/26 15:02
 * @Version 1.0
 * @Description: 服务注册接口
 */

public interface ServiceRegistry {
    Integer REDIS_REGISTRY = 0;
    Integer NACOS_REGISTRY = 1;
    Integer DEFAULT_REGISTRY = NACOS_REGISTRY;

    /**
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     **/
    void registry(String serviceName, InetSocketAddress inetSocketAddress);

    static ServiceRegistry getByCode(String URI,int code){
        switch (code){
            case 0:
                if(URI!=null) RedisUtil.setServerAddr(URI);
                return new RedisRegistry();
            case 1:
                if(URI!=null) NacosUtil.setServerAddr(URI);
                return new NacosRegistry();
            default:
                return null;
        }
    }

    void clearRegistry();

}
