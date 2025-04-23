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
import java.math.BigDecimal;
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
@TableName("ai_content_analysis")
public class AiContentAnalysis implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分析ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内容ID
     */
    @TableField("content_id")
    private Long contentId;

    /**
     * AI生成摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 标签
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 置信度
     */
    @TableField("confidence")
    private BigDecimal confidence;

    /**
     * 分析时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;


}
