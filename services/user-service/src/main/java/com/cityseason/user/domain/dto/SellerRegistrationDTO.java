package com.cityseason.user.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 卖家注册信息DTO
 */
@Data
public class SellerRegistrationDTO {

    /**
     * 用户id，关联user.id
     */
    @NotNull
    private Long userId;

    /**
     * 身份证号
     */
    @NotNull
    @Pattern(regexp = "^[1-9]\\d{16}[0-9X]$", message = "身份证号格式不正确")
    private String idNumber;

    /**
     * 姓名
     */
    @NotNull
    private String name;

    /**
     * 收款账户
     */
    @NotNull
    @Pattern(regexp = "^\\d{19}$", message = "收款账户格式不正确")
    private String receivingAccount;

}
