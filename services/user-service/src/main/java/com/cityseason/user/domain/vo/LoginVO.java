package com.cityseason.user.domain.vo;

import com.cityseason.user.domain.po.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {

    /**
     * 用户信息
     */
    private UserVO userVO;

    /**
     * 钱包数据
     */
    private Wallet wallet;

    /**
     * JWT令牌
     */
    private String token;

}
