package cn.lzheng.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Instance
 * @Author 6yi
 * @Date 2021/4/26 20:32
 * @Version 1.0
 * @Description:
 */


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcInstance {

    String host;

    Integer port;

    /*
     * 权重
     * */
    Integer weight;

    /*
     * 是否下线
     * */

    Boolean health;
}
