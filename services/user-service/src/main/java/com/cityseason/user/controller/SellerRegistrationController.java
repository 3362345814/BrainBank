package com.cityseason.user.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户管理
 *
 * @author 林心海
 * @since 2025-04-25
 */
@RestController
@RequestMapping("/seller-registration")
public class SellerRegistrationController {

    /**
     * 商户注册
     */
    @PostMapping("/register")
    public void register() {

    }

}
