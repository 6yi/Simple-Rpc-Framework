package cn.lzheng.rpc.spring;

import cn.lzheng.rpc.transport.JDKSocket.client.SocketClient;
import cn.lzheng.rpc.transport.RpcClientProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SimpleRpcConfiguration
 * @Author 6yi
 * @Date 2021/5/3 16:44
 * @Version 1.0
 * @Description:
 */

@Configuration
public class SimpleRpcConfiguration {


    public RpcClientProxy socketClient(){
        SocketClient client=null;
        client=startClient(1,0);
        return new RpcClientProxy(client);
    }

    @Bean
    public SpringBeanPostProcessor springBeanPostProcessor(){
        return new SpringBeanPostProcessor(socketClient());
    }

    public SocketClient startClient(int register,int serializer){
        return new SocketClient(register,serializer);
    }

}
