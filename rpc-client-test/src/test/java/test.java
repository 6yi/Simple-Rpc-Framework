import cn.lzheng.rpc.api.HelloObject;
import cn.lzheng.rpc.api.HelloService;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.JDKSocket.client.SocketClient;
import cn.lzheng.rpc.transport.RpcClientProxy;

/**
 * @ClassName test
 * @Author 6yi
 * @Date 2021/5/1 2:03
 * @Version 1.0
 * @Description:
 */


public class test {
    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketClient);
        HelloService proxy = rpcClientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(12, "??");
        System.out.println(proxy.hello(helloObject));
    }
}
