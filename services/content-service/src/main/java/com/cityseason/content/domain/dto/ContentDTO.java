package com.cityseason.content.domain.dto;


import com.baomidou.mybatisplus.annotation.TableId;
import com.cityseason.content.domain.enums.ContentPublishStatus;
import com.cityseason.content.domain.enums.ContentSaleMode;
import com.cityseason.content.domain.enums.ContentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {

    /**
     * 主键，雪花算法
     */
    private Long id;
    /**
     * 作者ID，关联 user.id
     */
    @NotNull(message = "作者ID不能为空")
    private Long userId;

    /**
     * 内容标题
     */
    @NotBlank(message = "内容标题不能为空")
    @Size(max = 255, message = "内容标题长度不能超过255个字符")
    private String title;

    /**
     * 内容简要介绍
     */
    @Size(max = 500, message = "内容简要介绍长度不能超过500个字符")
    private String description;

    /**
     * 封面图片路径
     */
    @NotBlank(message = "封面图片路径不能为空")
    private String previewImageUrl;

    /**
     * 正文文件路径（OSS / IPFS等）
     */
    @NotBlank(message = "正文文件路径不能为空")
    private String contentUrl;

    /**
     * 内容类型（1=文章，2=视频，3=音频，4=其他）
     */
    @NotNull(message = "内容类型不能为空")
    private ContentType type;

    /**
     * 图片列表
     */
    private List<ImageDTO> images;

    /**
     * 买断原价
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "买断原价不能为负数")
    private BigDecimal originalPrice;

    /**
     * 买断优惠价
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "买断优惠价不能为负数")
    private BigDecimal preferentialPrice;

    /**
     * 月订阅价格
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "月订阅价格不能为负数")
    private BigDecimal monthlySubscriptionPrice;

    /**
     * 季订阅价格
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "季订阅价格不能为负数")
    private BigDecimal quarterlySubscriptionPrice;

    /**
     * 年订阅价格
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "年订阅价格不能为负数")
    private BigDecimal annualSubscriptionPrice;

    /**
     * 销售模式（0= 免费，1=仅买断，2=仅订阅，3=买断+订阅）
     */
    @NotNull(message = "销售模式不能为空")
    private ContentSaleMode saleMode;

    /**
     * 发布状态（0=草稿，1=待审核，2=已上架，3=已下架，4=驳回）
     */
    @NotNull(message = "发布状态不能为空")
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

