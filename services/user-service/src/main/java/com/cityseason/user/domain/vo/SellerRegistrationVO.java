package com.cityseason.user.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerRegistrationVO {

    /**
     * 主键
     */
    private Long id;


    /**
     * 用户id
     */
    private Long userId;


    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 姓名
     */
    private String name;

    /**
     * 收款账户
     */
    private String receivingAccount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
