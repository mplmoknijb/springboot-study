package cn.leon.core.enums;

import cn.leon.core.model.LockException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum LockTypeEnum {
    REDIS(1, "redis"),
    MYSQL(2, "mysql"),
    ZOOKEEPER(3, "zookeeper");
    @Getter
    private int code;
    @Getter
    private String des;


    LockTypeEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public static LockTypeEnum valueOf(int code) {
        return Arrays.stream(values()).filter(type -> type.code == code)
                .findFirst().orElseThrow(() -> new LockException("类型不匹配"));
    }

    public static Set<String> valueSet() {
        return Arrays.stream(values()).map(LockTypeEnum::getDes)
                .collect(Collectors.toSet());
    }
}
