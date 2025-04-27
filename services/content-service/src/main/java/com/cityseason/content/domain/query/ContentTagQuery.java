package com.cityseason.content.domain.query;

import com.cityseason.common.domain.query.PageQuery;
import com.mysql.cj.AbstractQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContentTagQuery extends PageQuery {
    /**
     * 内容id
     */
    private Long contentId;
    /**
     * 标签名
     */
    private String tagName;
    /**
     * 标签id
     */
    private Long tagId;
}
