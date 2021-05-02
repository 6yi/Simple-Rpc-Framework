package cn.lzheng.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RedisInstance
 * @Author 6yi
 * @Date 2021/4/26 16:10
 * @Version 1.0
 * @Description:  redis注册中心获取的实例
 */


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisInstance {

    String host;

    Integer port;

    /*
    * 权重
    * */
    Double weight;

    /*
    * 是否下线
    * */

    Boolean health;

    public RpcInstance toInstance(){
        return RpcInstance.builder().host(host).port(port).weight(weight).health(health).build();
    }

}
