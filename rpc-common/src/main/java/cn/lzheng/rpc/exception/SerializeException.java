package cn.lzheng.rpc.exception;

/**
 * @ClassName SerializeException
 * @Author 6yi
 * @Date 2021/4/27 17:02
 * @Version 1.0
 * @Description:
 */


public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}
