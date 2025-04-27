package com.cityseason.content.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serial;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2025-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，雪花算法
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 评论者ID，关联 user.id
     */
    private Long userId;

    /**
     * 评论内容ID，关联 content.id
     */
    private Long contentId;

    /**
     * 父评论ID（0表示一级评论）
     */
    private Long parentId;

    /**
     * 评论正文
     */
    private String text;

    /**
     * 评分（1-5星）
     */
    private Integer rating;

    /**
     * 评论时间
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 是否内容所有者
     */
    private Boolean isOwner;


}
