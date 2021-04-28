package cn.lzheng.rpc.provider;

/**
 * @ClassName ServiceProvider
 * @Author 6yi
 * @Date 2021/4/28 19:32
 * @Version 1.0
 * @Description:
 */

public interface ServiceProvider {

    <T> void addServiceProvider(T service,String serviceName);

    Object getServiceProvider(String serviceName);

}
