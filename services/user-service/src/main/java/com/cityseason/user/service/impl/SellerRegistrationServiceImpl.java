package com.cityseason.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cityseason.api.domin.enums.UserRole;
import com.cityseason.common.annotation.AddCache;
import com.cityseason.common.annotation.DelCache;
import com.cityseason.common.util.RequestContext;
import com.cityseason.common.util.VerificationCode;
import com.cityseason.user.domain.dto.SellerRegistrationDTO;
import com.cityseason.user.domain.po.SellerRegistration;
import com.cityseason.user.domain.po.User;
import com.cityseason.user.domain.vo.SellerRegistrationVO;
import com.cityseason.user.mapper.SellerRegistrationMapper;
import com.cityseason.user.mapper.UserMapper;
import com.cityseason.user.service.ISellerRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-25
 */
@Service
@RequiredArgsConstructor
public class SellerRegistrationServiceImpl extends ServiceImpl<SellerRegistrationMapper, SellerRegistration> implements ISellerRegistrationService {

    private final UserMapper userMapper;

    /**
     * 商户注册
     *
     * @return 注册信息
     */
    @Override
    public SellerRegistrationVO register(@Valid SellerRegistrationDTO sellerRegistrationDTO) {
        // 验证id是否是当前用户
        if (!RequestContext.getCurrentUserId().equals(sellerRegistrationDTO.getUserId())) {
            throw new RuntimeException("无权限");
        }

        // 验证用户是否已经注册
        SellerRegistration existingRegistration = getOne(new LambdaQueryWrapper<SellerRegistration>()
                .eq(SellerRegistration::getUserId, RequestContext.getCurrentUserId()));
        if (existingRegistration != null) {
            throw new RuntimeException("用户已被注册");
        }
        // 验证身份证号是否已经注册
        existingRegistration = getOne(new LambdaQueryWrapper<SellerRegistration>()
                .eq(SellerRegistration::getIdNumber, sellerRegistrationDTO.getIdNumber()));
        if (existingRegistration != null) {
            throw new RuntimeException("身份证号已被注册");
        }

        // TODO: 实名验证

        // 保存注册信息
        SellerRegistration sellerRegistration = BeanUtil.copyProperties(sellerRegistrationDTO, SellerRegistration.class);
        sellerRegistration.setUserId(RequestContext.getCurrentUserId());
        save(sellerRegistration);

        // 将用户角色设置为商户
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        user.setRole(UserRole.CREATOR);
        userMapper.updateById(user);

        // 返回注册信息
        return BeanUtil.copyProperties(sellerRegistration, SellerRegistrationVO.class);
    }

    @Override
    @AddCache(prefix = "user:seller_registration")
    public SellerRegistrationVO getRegistrationInfo(Long userId) {

        SellerRegistration sellerRegistration = getOne(new LambdaQueryWrapper<SellerRegistration>()
                .eq(SellerRegistration::getUserId, userId));

        SellerRegistrationVO sellerRegistrationVO = BeanUtil.copyProperties(sellerRegistration, SellerRegistrationVO.class);
        // 隐藏敏感信息
        if (sellerRegistration != null) {
            // 身份证号保留前1位和后1位
            sellerRegistrationVO.setIdNumber(sellerRegistration.getIdNumber().charAt(0) + "****************" + sellerRegistration.getIdNumber().substring(17));
            // 姓名大于等于3位的，保留前1位和后1位，小于3位的，保留第一个字
            if (sellerRegistration.getName().length() >= 3) {
                sellerRegistrationVO.setName(sellerRegistration.getName().charAt(0) + "*" + sellerRegistration.getName().substring(sellerRegistration.getName().length() - 1));
            } else {
                sellerRegistrationVO.setName(sellerRegistration.getName().charAt(0) + "*");
            }
            // 收款账户保留前4位和后4位
            sellerRegistrationVO.setReceivingAccount(sellerRegistration.getReceivingAccount().substring(0, 4) + "***********" + sellerRegistration.getReceivingAccount().substring(sellerRegistration.getReceivingAccount().length() - 4));
        }

        return sellerRegistrationVO;
    }

    @Override
    @DelCache(prefix = "user:seller_registration")
    public SellerRegistrationVO modifyReceiveAccount(Long userId, String verificationCode, String receivingAccount) {
        // 验证是否是当前用户
        if (!RequestContext.getCurrentUserId().equals(userId)) {
            throw new RuntimeException("无权限");
        }

        // 验证验证码是否正确
        User user = userMapper.selectById(userId);
        if (!VerificationCode.verify(user.getPhone(), verificationCode)) {
            throw new RuntimeException("验证码错误");
        }

        // 修改收款账户
        SellerRegistration sellerRegistration = getOne(new LambdaQueryWrapper<SellerRegistration>()
                .eq(SellerRegistration::getUserId, userId));

        // 验证收款账户
        if (sellerRegistration.getReceivingAccount().equals(receivingAccount)) {
            throw new RuntimeException("收款账户和原来相同");
        }
        // 验证收款账户格式是否正确
        if (!receivingAccount.matches("^\\d{19}$")) {
            throw new RuntimeException("收款账户格式不正确");
        }

        sellerRegistration.setReceivingAccount(receivingAccount);
        updateById(sellerRegistration);

        return BeanUtil.copyProperties(sellerRegistration, SellerRegistrationVO.class);
    }

    @Override
    @DelCache(prefix = "user:seller_registration")
    public void deleteSellerAccount(Long userId, String verificationCode) {
        // 验证是否是当前用户
        if (!RequestContext.getCurrentUserId().equals(userId)) {
            throw new RuntimeException("无权限");
        }

        // 验证验证码是否正确
        User user = userMapper.selectById(userId);
        if (!VerificationCode.verify(user.getPhone(), verificationCode)) {
            throw new RuntimeException("验证码错误");
        }

        // 删除商户账户
        remove(new LambdaQueryWrapper<SellerRegistration>()
                .eq(SellerRegistration::getUserId, userId));

        // 将用户角色设置为普通用户
        user.setRole(UserRole.ORDINARY);
        userMapper.updateById(user);
    }
}
