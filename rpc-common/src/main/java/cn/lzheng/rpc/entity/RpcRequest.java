package cn.lzheng.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @ClassName RpcRequest
 * @Author 6yi
 * @Date 2021/4/25 16:28
 * @Version 1.0
 * @Description: RPC请求消息实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 请求号
     */
    private String requestId;

    /*
    * 调用接口
    * */
    private String interfaceName;

    /*
    * 调用方法
    * */
    private String method;

    /*
    * 方法调用参数
    * */
    private Object[] args;

    /*
    * 调用方法的参数类型
    * */
    private Class<?>[] argsType;

    /*
     * 是否是心跳包
     **/
    private Boolean heartBeat;

}
