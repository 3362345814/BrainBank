package com.cityseason.content.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cityseason.content.domain.enums.ContentPublishStatus;
import com.cityseason.content.domain.enums.ContentSaleMode;
import com.cityseason.content.domain.enums.ContentType;
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
@TableName("content")
public class Content implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 作者ID
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容简介
     */
    @TableField("description")
    private String description;

    /**
     * 介绍页图片路径
     */
    @TableField("preview_image_url")
    private String previewImageUrl;

    /**
     * 文件路径
     */
    @TableField("content_url")
    private String contentUrl;

    /**
     * 内容类型
     */
    @TableField("type")
    private ContentType type;

    /**
     * 单次购买价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 销售模式
     */
    @TableField("sale_mode")
    private ContentSaleMode saleMode;

    /**
     * 发布状态
     */
    @TableField("publish_status")
    private ContentPublishStatus publishStatus;

    /**
     * 标签
     */
    @TableField("tags")
    private String tags;

    /**
     * 浏览次数
     */
    @TableField("view_count")
    private Long viewCount;

    /**
     * 购买次数
     */
    @TableField("purchase_count")
    private Long purchaseCount;

    /**
     * 推送权重
     */
    @TableField("boost_weight")
    private BigDecimal boostWeight;

    /**
     * 推荐权重生效截止时间
     */
    @TableField("recommend_expire_at")
    private LocalDateTime recommendExpireAt;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;


}
