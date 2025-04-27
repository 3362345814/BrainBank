package com.cityseason.content.domain.vo;

import com.cityseason.content.domain.enums.ContentPublishStatus;
import com.cityseason.content.domain.enums.ContentSaleMode;
import com.cityseason.content.domain.enums.ContentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class ContentVO {
    /**
     * 作者ID，关联 user.id
     */
    private Long userId;

    /**
     * 内容标题
     */
    private String title;

    /**
     * 内容简要介绍
     */
    private String description;

    /**
     * 封面图片路径
     */
    private String previewImageUrl;

    /**
     * 正文文件路径（OSS / IPFS等）
     */
    private String contentUrl;

    /**
     * 内容类型（1=文章，2=视频，3=音频，4=其他）
     */
    private ContentType type;

    /**
     * 买断原价
     */
    private BigDecimal originalPrice;

    /**
     * 买断优惠价
     */
    private BigDecimal preferentialPrice;

    /**
     * 月订阅价格
     */
    private BigDecimal monthlySubscriptionPrice;

    /**
     * 季订阅价格
     */
    private BigDecimal quarterlySubscriptionPrice;

    /**
     * 年订阅价格
     */
    private BigDecimal annualSubscriptionPrice;

    /**
     * 销售模式（0= 免费，1=仅买断，2=仅订阅，3=买断+订阅）
     */
    private ContentSaleMode saleMode;

    /**
     * 发布状态（0=草稿，1=待审核，2=已上架，3=已下架，4=驳回）
     */
    private ContentPublishStatus publishStatus;

    /**
     * 浏览次数
     */
    private Long viewCount = 0L;

    /**
     * 购买次数
     */
    private Long purchaseCount = 0L;

    /**
     * 收藏次数
     */
    private Long favoriteCount = 0L;

    /**
     * 点赞次数
     */
    private Long likeCount = 0L;

    /**
     * 推送权重，默认1.0，越高越优先被推荐
     */
    private BigDecimal boostWeight = BigDecimal.valueOf(1.0);

    /**
     * 推荐权重截止时间
     */
    private LocalDateTime recommendExpireAt;

    /**
     * 创建时间
     */

    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 最后修改时间
     */
    private LocalDateTime updatedAt = LocalDateTime.now();
}
