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
@TableName("report")
public class Report implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 举报人ID
     */
    private Long userId;

    /**
     * 被举报内容ID
     */
    private Long contentId;

    /**
     * 举报理由
     */
    private String reason;

    /**
     * 处理状态（0=待处理，1=已处理）
     */
    private Integer status;

    /**
     * 举报时间
     */
    private LocalDateTime createdAt = LocalDateTime.now();


}
