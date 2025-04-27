package com.cityseason.content.domain.query;

import com.cityseason.common.domain.query.PageQuery;
import com.cityseason.content.domain.enums.ContentPublishStatus;
import com.cityseason.content.domain.enums.ContentSaleMode;
import com.cityseason.content.domain.enums.ContentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContentQuery extends PageQuery {
    /**
     * 内容类型（1=文章，2=视频，3=音频，4=其他）
     */
    private ContentType type;
    /**
     * 销售模式（0= 免费，1=仅买断，2=仅订阅，3=买断+订阅）
     */
    private ContentSaleMode saleMode;

    /**
     * 发布状态（0=草稿，1=待审核，2=已上架，3=已下架，4=驳回）
     */
    private ContentPublishStatus publishStatus;
    /**
     * 作者姓名
     */
    private Long username;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者ID
     */
    private Long userId;

}
