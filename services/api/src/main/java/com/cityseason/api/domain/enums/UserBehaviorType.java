package com.cityseason.api.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserBehaviorType {
    LOOK(1, "浏览", 0.05),
    COLLECT(2, "收藏", 0.2),
    LIKE(3, "点赞", 0.1),
    SHARE(4, "分享", 0.1),
    PURCHASE(5, "购买", 0.4);


    @EnumValue
    @JsonValue
    public final int code;
    public final String desc;
    public final double value;

    UserBehaviorType(int code, String desc, double value) {
        this.code = code;
        this.desc = desc;
        this.value = value;
    }

    public static UserBehaviorType of(int code) {
        for (UserBehaviorType type : UserBehaviorType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的类型：" + code);
    }
}
