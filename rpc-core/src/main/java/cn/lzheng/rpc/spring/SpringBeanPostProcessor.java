package cn.lzheng.rpc.spring;

import cn.lzheng.rpc.annotation.RpcReference;
import cn.lzheng.rpc.transport.RpcClientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

/**
 * @ClassName SpringBeanPostProcessor
 * @Author 6yi
 * @Date 2021/5/3 12:39
 * @Version 1.0
 * @Description:
 */

@Slf4j
public class SpringBeanPostProcessor implements BeanPostProcessor{

    private RpcClientProxy rpcClient;

    public SpringBeanPostProcessor(RpcClientProxy rpcClient) {
        this.rpcClient = rpcClient;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RpcReference rpcReference = declaredField.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                Object clientProxy = rpcClient.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

}
