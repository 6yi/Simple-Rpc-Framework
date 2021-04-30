package cn.lzheng.rpc.entity;

import cn.lzheng.rpc.enumeration.ResponseCode;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

/**
 * @ClassName RpcResponse
 * @Author 6yi
 * @Date 2021/4/25 16:33
 * @Version 1.0
 * @Description:
 */

@Data
public class RpcResponse<T> implements Serializable {

    /**
     * 响应对应的请求号
     */
    private String requestId;

    /*
    * 响应的状态码
    * */
    private Integer statusCode;

    /*
    * 响应信息
    * */
    private String message;

    /*
    * 响应数据
    * */
    private T data;

    public static <T> RpcResponse<T> success(T data,String requestId){
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setStatusCode(200);
        rpcResponse.setData(data);
        rpcResponse.setRequestId(requestId);
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

}
