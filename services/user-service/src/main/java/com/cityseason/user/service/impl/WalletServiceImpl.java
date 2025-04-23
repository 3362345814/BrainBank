package com.cityseason.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cityseason.common.annotation.AddCache;
import com.cityseason.common.annotation.DelCache;
import com.cityseason.common.util.RequestContext;
import com.cityseason.user.domain.po.User;
import com.cityseason.user.domain.po.Wallet;
import com.cityseason.user.mapper.UserMapper;
import com.cityseason.user.mapper.WalletMapper;
import com.cityseason.user.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
@Service
@RequiredArgsConstructor
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements IWalletService {

    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DelCache(prefix = "user:wallet")
    public Wallet deductBalance(Long userId, BigDecimal money) {
        // 1. 获取用户ID（从安全上下文）
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        if (user == null || !user.getId().equals(userId)) {
            throw new RuntimeException("无权限");
        }

        // 2. 验证支付密码（如果需要）
        // verifyPaymentPassword(userId, deductBalanceDTO.getPaymentPassword());

        // 3. 查询并锁定钱包记录（悲观锁方式）
        Wallet wallet = getWalletByUserId(user.getId());

        // 4. 检查余额是否充足
        if (money.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("金额必须大于零");
        }

        if (wallet.getBalance().compareTo(money) < 0) {
            throw new RuntimeException("余额不足");
        }

        // 5. 更新钱包余额
        wallet.setBalance(wallet.getBalance().subtract(money));
        wallet.setTotalExpense(wallet.getTotalExpense().add(money));


        updateById(wallet);
        // 6. 返回扣款结果
        return wallet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DelCache(prefix = "user:wallet")
    public Wallet addBalance(Long userId, BigDecimal money) {
        // 1. 获取用户ID（从安全上下文）
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        if (user == null || !user.getId().equals(userId)) {
            throw new RuntimeException("无权限");
        }

        // 2. 验证支付密码（如果需要）
        // verifyPaymentPassword(userId, deductBalanceDTO.getPaymentPassword());

        // 3. 查询并锁定钱包记录（悲观锁方式）
        Wallet wallet = getWalletByUserId(user.getId());

        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("金额必须大于零");
        }

        // 5. 更新钱包余额
        wallet.setBalance(wallet.getBalance().add(money));
        wallet.setTotalIncome(wallet.getTotalIncome().add(money));

        updateById(wallet);
        // 6. 返回扣款结果
        return wallet;
    }

    @Override
    @AddCache(prefix = "user:wallet", expire = 1800)
    public Wallet getWalletByUserId(Long userId) {
        LambdaQueryWrapper<Wallet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Wallet::getUserId, userId);
        Wallet wallet = getOne(wrapper);
        if (wallet == null) {
            throw new RuntimeException("钱包不存在");
        }

        return wallet;
    }


}
