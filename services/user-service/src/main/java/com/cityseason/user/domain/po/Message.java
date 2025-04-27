package com.cityseason.user.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cityseason.user.domain.enums.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 林心海
 * @since 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("message")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 消息标题
     */
    @TableField("title")
    private String title;

    /**
     * 消息正文
     */
    @TableField("content")
    private String content;

    /**
     * 发送者
     */
    @TableField("sender_id")
    private Long senderId;

    /**
     * 接收者
     */
    @TableField("receiver_id")
    private Long receiverId;

    /**
     * 消息类型（0=系统通知，1=活动消息，2=创作者通知，3=私信）
     */
    @TableField("type")
    private MessageType type;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 是否已读
     */
    @TableField("is_read")
    private Boolean isRead = false;

    /**
     * 阅读时间
     */
    @TableField("read_at")
    private LocalDateTime readAt;


}
