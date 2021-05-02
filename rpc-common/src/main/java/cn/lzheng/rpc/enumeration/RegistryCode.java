package cn.lzheng.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegistryCode {

    REDIS(0),
    NACOS(1);

    private final int code;
}
