package cn.lzheng.rpc.exception;

import cn.lzheng.rpc.enumeration.RpcError;

/**
 * @ClassName RpcException
 * @Author 6yi
 * @Date 2021/4/26 20:26
 * @Version 1.0
 * @Description:
 */


public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}