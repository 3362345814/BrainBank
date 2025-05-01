package com.cityseason.recommend.domain.dto;

import com.cityseason.recommend.domain.enums.UserBehaviorType;
import lombok.Data;

/**
 * 用户行为日志DTO
 */
@Data
public class UserBehaviorLogDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 相关内容id
     */
    private Long contentId;

    /**
     * 行为类型
     */
    private UserBehaviorType behaviorType;

    /**
     * 行为持续时间
     */
    private Integer duration;
}
