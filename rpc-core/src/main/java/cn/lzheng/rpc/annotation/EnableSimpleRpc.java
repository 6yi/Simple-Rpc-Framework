package cn.lzheng.rpc.annotation;

import cn.lzheng.rpc.spring.SimpleRpcConfiguration;
import org.springframework.context.annotation.Import;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import(SimpleRpcConfiguration.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableSimpleRpc {

}
