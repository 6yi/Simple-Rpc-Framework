package cn.lzheng.rpc.transport;

/**
 * @ClassName RpcServer
 * @Author 6yi
 * @Date 2021/4/26 14:47
 * @Version 1.0
 * @Description:
 */

public interface RpcServer {

    void start();

    <T> void publishService(T service, String serviceName);


}
