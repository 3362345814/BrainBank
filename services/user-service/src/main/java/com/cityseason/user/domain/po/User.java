package com.cityseason.user.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cityseason.user.domain.enums.UserRole;
import com.cityseason.user.domain.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 加密密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户头像链接
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 角色
     */
    @TableField("role")
    private UserRole role = UserRole.ORDINARY;

    /**
     * 账户状态
     */
    @TableField("status")
    private UserStatus status = UserStatus.NORMAL;

    /**
     * 最近登录时间
     */
    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    /**
     * 邀请码
     */
    @TableField("invite_code")
    private String inviteCode;

    /**
     * 个人简介
     */
    @TableField("description")
    private String description;

    /**
     * 注册时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 修改时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}
