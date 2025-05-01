package com.cityseason.api.domain.vo;

import com.cityseason.recommend.domain.enums.UserBehaviorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBehaviorLogVO {

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
