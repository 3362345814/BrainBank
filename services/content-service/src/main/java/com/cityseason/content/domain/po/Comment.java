package com.cityseason.content.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2025-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 评论内容ID
     */
    @TableField("content_id")
    private Long contentId;

    /**
     * 父评论ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 评论正文
     */
    @TableField("text")
    private String text;

    /**
     * 评分
     */
    @TableField("rating")
    private Integer rating;

    /**
     * 评论时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;


}
