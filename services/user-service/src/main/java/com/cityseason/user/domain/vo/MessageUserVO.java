package com.cityseason.user.domain.vo;

import com.cityseason.user.domain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 发送消息VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUserVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息正文
     */
    private String content;

    /**
     * 发送者
     */
    private Long senderId;

    /**
     * 接收者
     */
    private Long receiverId;

    /**
     * 消息类型（0=系统通知，1=活动消息，2=创作者通知，3=私信）
     */
    private MessageType type;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 是否已读
     */
    private Boolean isRead = false;

    /**
     * 阅读时间
     */
    private LocalDateTime readAt;

}
