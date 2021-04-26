package cn.lzheng.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ResponseCode
 * @Author 6yi
 * @Date 2021/4/26 0:51
 * @Version 1.0
 * @Description:
 */

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FOUND(500, "未找到指定方法"),
    CLASS_NOT_FOUND(500, "未找到指定类");

    private final int code;
    private final String message;


}
