package com.cityseason.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.user.domain.po.Wallet;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
public interface IWalletService extends IService<Wallet> {

    Wallet deductBalance(Long userId, BigDecimal money);

    Wallet addBalance(Long userId, BigDecimal money);

    Wallet getWalletByUserId(Long userId);

}
