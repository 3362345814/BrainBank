package com.cityseason.common.domain.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 分页查询
 *
 * @author 林心海
 */
@Data
public class PageQuery {

    /**
     * 页数
     */
    private Long pageNo;
    
    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 排序顺序（默认升序）
     */
    private Boolean isAsc = false;


    public <T> Page<T> toMpPage(OrderItem... orders) {
        // 1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        // 2.排序条件
        // 2.1.先看前端有没有传排序字段
        if (StringUtils.hasText(sortBy)) {
            p.addOrder(new OrderItem().setAsc(isAsc).setColumn(sortBy));
            return p;
        }
        // 2.2.再看有没有手动指定排序字段
        if (orders != null) {
            p.addOrder(orders);
        }
        return p;
    }

    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc) {
        return this.toMpPage(new OrderItem().setAsc(isAsc).setColumn(defaultSortBy));
    }

    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toMpPage("create_time", false);
    }

    public <T> Page<T> toMpPageDefaultSortByUpdateTimeDesc() {
        return toMpPage("update_time", false);
    }
}