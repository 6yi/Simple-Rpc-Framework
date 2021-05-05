package cn.lzheng.rpc.test;

import cn.lzheng.rpc.annotation.RpcReference;
import cn.lzheng.rpc.api.HelloObject;
import cn.lzheng.rpc.api.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @ClassName cn.lzheng.rpc.test.H
 * @Author 6yi
 * @Date 2021/5/3 12:52
 * @Version 1.0
 * @Description:
 */

@Controller
public class testService {

    @RpcReference
    HelloService helloService;


    public void setHelloService() {
        helloService.hello(new HelloObject());
    }

    public HelloService getHelloService() {
        return helloService;
    }
}
