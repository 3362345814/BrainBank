package com.cityseason.content.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ContentType {
    ARTICLE(1, "文章"),
    VIDEO(2, "视频"),
    AUDIO(3, "音频"),
    OTHER(4, "其他");


    @EnumValue
    @JsonValue
    public final int code;
    public final String desc;

    ContentType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
