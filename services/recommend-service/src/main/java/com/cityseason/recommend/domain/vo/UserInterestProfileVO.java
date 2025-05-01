package com.cityseason.recommend.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInterestProfileVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 偏好强度（0~1）
     */
    private BigDecimal preference;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
