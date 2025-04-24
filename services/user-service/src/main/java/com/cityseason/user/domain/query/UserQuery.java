package com.cityseason.user.domain.query;

import com.cityseason.common.domain.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户查询请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends PageQuery {

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 注册时间
     */
    private LocalDateTime lastLoginAtMin;

    /**
     * 注册时间
     */
    private LocalDateTime lastLoginAtMax;

}
