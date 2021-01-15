package com.yao.app.json.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Created by yaolei02 on 2018/9/5.
 */
public enum CodeEnum {
    TIPS(1, "hello"),
    TIPS2(2, "world");

    private final int code;

    private final String desc;

    CodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, CodeEnum> maps = Maps.newHashMap();

    static {
        for (CodeEnum codeEnum : CodeEnum.values()) {
            maps.put(codeEnum.code, codeEnum);
        }
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static CodeEnum findByValue(int value) {
        return maps.get(value);
    }
}
