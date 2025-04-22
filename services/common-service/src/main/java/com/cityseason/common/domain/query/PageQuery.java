package com.cityseason.common.domain.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@Schema(description = "分页查询参数")
public class PageQuery {
    @Schema(description = "页码")
    private Long pageNo;
    @Schema(description = "每页大小")
    private Long pageSize;
    @Schema(description = "排序字段")
    private String sortBy;
    @Schema(description = "是否升序")
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