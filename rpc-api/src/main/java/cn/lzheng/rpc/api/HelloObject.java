package cn.lzheng.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName HelloObject
 * @Author 6yi
 * @Date 2021/4/25 16:04
 * @Version 1.0
 * @Description: api测试实体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloObject implements Serializable {

    private Integer id;
    private String message;

}
