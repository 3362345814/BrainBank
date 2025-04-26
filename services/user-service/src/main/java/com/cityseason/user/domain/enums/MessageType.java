package com.cityseason.user.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MessageType {
    SYSTEM(0, "系统消息"),
    PROMOTION(1, "活动消息"),
    CREATOR(2, "创作者消息"),
    PRIVATE(3, "私信消息");

    @EnumValue
    @JsonValue
    public final int code;
    public final String desc;

    MessageType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
