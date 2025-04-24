package com.cityseason.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录信息DTO
 */
@Data
public class LoginDTO {

    /**
     * 邮箱/手机号
     */
    @NotBlank(message = "登录账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
