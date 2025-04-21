package com.cityseason.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "登录请求参数")
@Data
public class LoginDTO {

    /**
     * 邮箱/手机号
     */
    @Schema(description = "邮箱/手机号")
    @NotBlank(message = "登录账号不能为空")
    private String account;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
