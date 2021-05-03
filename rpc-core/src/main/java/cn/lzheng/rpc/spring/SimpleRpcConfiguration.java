package cn.lzheng.rpc.spring;

import cn.lzheng.rpc.transport.JDKSocket.client.SocketClient;
import cn.lzheng.rpc.transport.RpcClient;
import cn.lzheng.rpc.transport.RpcClientProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName SimpleRpcConfiguration
 * @Author 6yi
 * @Date 2021/5/3 16:44
 * @Version 1.0
 * @Description:
 */

@Configuration
@Component
public class SimpleRpcConfiguration {

    @Resource
    RpcClient rpcClient;

    @Bean
    public SpringBeanPostProcessor springBeanPostProcessor(){
        return new SpringBeanPostProcessor(rpcClientProxy());
    }

    public RpcClientProxy rpcClientProxy(){
        return new RpcClientProxy(rpcClient);
    }

}
