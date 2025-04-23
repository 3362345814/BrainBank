package com.cityseason.content.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ContentPublishStatus {
    DRAFT(0, "草稿"),
    REVIEW(1, "待审核"),
    PUBLISH(2, "已上架"),
    OFF_SHELF(3, "已下架"),
    REJECT(4, "驳回");


    @EnumValue
    @JsonValue
    public final int code;
    public final String desc;

    ContentPublishStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
