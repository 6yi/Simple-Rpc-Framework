import cn.lzheng.rpc.annotation.EnableSimpleRpc;
import cn.lzheng.rpc.annotation.RpcReference;
import cn.lzheng.rpc.api.HelloObject;
import cn.lzheng.rpc.api.HelloService;
import cn.lzheng.rpc.enumeration.RegistryCode;
import cn.lzheng.rpc.enumeration.SerializerCode;
import cn.lzheng.rpc.test.testService;
import cn.lzheng.rpc.transport.JDKSocket.client.SocketClient;
import cn.lzheng.rpc.transport.RpcClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName test
 * @Author 6yi
 * @Date 2021/5/1 2:03
 * @Version 1.0
 * @Description:
 */
@ComponentScan("cn.lzheng.rpc.test")
@Configuration
@EnableSimpleRpc
public class test {
    @Bean
    public RpcClient socketClient(){
        return new SocketClient("192.168.123.17:8848", RegistryCode.NACOS.getCode());
    }
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(test.class);
        testService bean = applicationContext.getBean(testService.class);
        bean.getHelloService().hello(new HelloObject(1,"hello"));
    }
}