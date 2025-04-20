package com.cityseason.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    /**
     * 用户名（可选）
     */
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20个字符之间")
    private String username;

    /**
     * 邮箱（可选）
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号（可选）
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 用户头像链接（可选）
     */
    private String avatarUrl;

    /**
     * 个人简介（可选）
     */
    @Size(max = 500, message = "个人简介最多500个字符")
    private String description;

}
