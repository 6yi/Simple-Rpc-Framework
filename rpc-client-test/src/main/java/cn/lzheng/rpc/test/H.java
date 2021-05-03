package cn.lzheng.rpc.test;

import cn.lzheng.rpc.annotation.RpcReference;
import cn.lzheng.rpc.api.HelloService;
import org.springframework.stereotype.Service;

/**
 * @ClassName cn.lzheng.rpc.test.H
 * @Author 6yi
 * @Date 2021/5/3 12:52
 * @Version 1.0
 * @Description:
 */

@Service
public class H {
    @RpcReference
    HelloService helloService;

    public HelloService getHelloService() {
        return helloService;
    }

    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
}
