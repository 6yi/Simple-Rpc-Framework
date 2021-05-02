package cn.lzheng.rpc.utils;

import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @ClassName NacosUtil
 * @Author 6yi
 * @Date 2021/5/2 14:57
 * @Version 1.0
 * @Description:
 */


public class NacosUtil {
    private static final Logger logger = LoggerFactory.getLogger(NacosUtil.class);
    private static NamingService namingService;
    private static Set<String> serviceNames;
    private static InetSocketAddress address;
    private static String SERVER_ADDR = "192.168.123.17:8848";



    private NacosUtil() {
    }

    private static void init(){
        if(namingService==null){
            synchronized (NacosUtil.class){
                if(namingService==null){
                    namingService = getNacosNamingService();
                    serviceNames = new HashSet<>();
                }
            }
        }
    }

    private static NamingService getNacosNamingService() {
        try {
            return NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            logger.error("连接到Nacos时有错误发生: ", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    public static void registerService(String serviceName, InetSocketAddress address) throws NacosException {
            init();
            namingService.registerInstance(serviceName,address.getHostString(),address.getPort());
            NacosUtil.address=address;
            serviceNames.add(serviceName);
    }

    public static List<Instance> getAllInstance(String serviceName) throws NacosException {
        init();
        return namingService.getAllInstances(serviceName);
    }

    public static void clearRegistry() {
        init();
        if(!serviceNames.isEmpty() && address != null) {
            String host = address.getHostName();
            int port = address.getPort();
            Iterator<String> iterator = serviceNames.iterator();
            while(iterator.hasNext()) {
                String serviceName = iterator.next();
                try {
                    namingService.deregisterInstance(serviceName, host, port);
                } catch (NacosException e) {
                    logger.error("注销服务 {} 失败", serviceName, e);
                }
            }
        }
    }
}
