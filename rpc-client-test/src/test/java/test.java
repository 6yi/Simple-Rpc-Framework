import cn.lzheng.rpc.annotation.EnableSimpleRpc;
import cn.lzheng.rpc.api.HelloObject;
import cn.lzheng.rpc.test.H;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName test
 * @Author 6yi
 * @Date 2021/5/1 2:03
 * @Version 1.0
 * @Description:
 */
@Configuration
@ComponentScan("cn.lzheng.rpc.test")
@EnableSimpleRpc
public class test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(test.class);
        H bean = applicationContext.getBean(H.class);
        bean.getHelloService().hello(new HelloObject());

//        SocketClient socketClient = new SocketClient();
//        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketClient);
//        HelloService proxy = rpcClientProxy.getProxy(HelloService.class);
//        HelloObject helloObject = new HelloObject(12, "??");
//        System.out.println(proxy.hello(helloObject));
    }
}
