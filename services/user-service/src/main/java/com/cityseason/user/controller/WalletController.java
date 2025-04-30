package com.cityseason.user.controller;


import com.cityseason.api.domin.vo.Result;
import com.cityseason.log.annotation.OperationLog;
import com.cityseason.user.domain.po.Wallet;
import com.cityseason.user.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 钱包管理
 *
 * @author 林心海
 * @since 2025-04-19
 */
@RestController
@RequestMapping("/user/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final IWalletService walletService;

    /**
     * 扣减钱包余额
     *
     * @param money 扣减金额
     * @return 钱包实体
     */
    @PostMapping("/deduct")
    @OperationLog(module = "钱包", operation = "扣减余额")
    public Result<Wallet> deductBalance(@RequestParam Long userId, BigDecimal money) {
        try {
            Wallet userVO = walletService.deductBalance(userId, money);
            return Result.success(userVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 增加钱包余额
     *
     * @param money 增加金额
     * @return 钱包实体
     */
    @PostMapping("/add")
    @OperationLog(module = "钱包", operation = "增加余额")
    public Result<Wallet> addBalance(@RequestParam Long userId, BigDecimal money) {
        try {
            Wallet userVO = walletService.addBalance(userId, money);
            return Result.success(userVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }


}
