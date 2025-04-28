package com.cityseason.api.domin.vo;


import com.cityseason.api.domin.enums.UserRole;
import com.cityseason.api.domin.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户头像链接
     */
    private String avatarUrl;

    /**
     * 角色
     */
    private UserRole role;

    /**
     * 账户状态
     */
    private UserStatus status;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginAt;

    /**
     * 个人简介
     */
    private String description;

    /**
     * 注册时间
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 修改时间
     */
    private LocalDateTime updatedAt = LocalDateTime.now();
}
