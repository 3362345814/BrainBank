package com.cityseason.user.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserRole {
    ORDINARY(0, "普通用户"),
    CREATOR(1, "创作者"),
    ADMIN(2, "管理员"),
    SUPER_ADMIN(3, "超级管理员");

    @EnumValue
    @JsonValue
    public final int code;
    public final String desc;

    UserRole(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserRole of(int code) {
        for (UserRole type : UserRole.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的用户类型：" + code);
    }
}
