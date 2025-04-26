package com.cityseason.user.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 消息DTO
 */
@Data
public class MessageDTO {

    /**
     * 消息标题
     */
    @NotNull(message = "消息标题不能为空")
    private String title;

    /**
     * 消息正文
     */
    @NotNull(message = "消息正文不能为空")
    private String content;

}
