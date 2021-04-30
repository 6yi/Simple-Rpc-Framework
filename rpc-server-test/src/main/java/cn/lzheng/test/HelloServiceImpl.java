package cn.lzheng.test;

import cn.lzheng.rpc.annotation.RpcService;
import cn.lzheng.rpc.api.HelloObject;
import cn.lzheng.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName HelloServiceImpl
 * @Author 6yi
 * @Date 2021/4/25 16:15
 * @Version 1.0
 * @Description:
 */

@RpcService
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "这是Impl1方法";
    }
}
