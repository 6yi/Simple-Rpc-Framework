package cn.lzheng.rpc.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName RedisInstance
 * @Author 6yi
 * @Date 2021/4/26 16:10
 * @Version 1.0
 * @Description:  redis注册中心获取的实例
 */

@Data
@Builder
public class RedisInstance {

    String host;

    int port;

    /*
    * 权重
    * */
    int weight;

    /*
    * 是否下线
    * */

    boolean health;

}
