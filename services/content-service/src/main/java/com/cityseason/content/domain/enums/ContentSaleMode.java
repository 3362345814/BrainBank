package com.cityseason.content.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ContentSaleMode {
    FREE(0, "免费"),
    BUY_OUT(1, "买断"),
    SUBSCRIBE(2, "订阅"),
    BY_OUT_OR_SUBSCRIBE(3, "买断或订阅");

    @EnumValue
    @JsonValue
    public final int code;
    public final String desc;

    ContentSaleMode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
