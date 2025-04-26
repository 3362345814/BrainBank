package com.cityseason.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.user.domain.dto.SellerRegistrationDTO;
import com.cityseason.user.domain.po.SellerRegistration;
import com.cityseason.user.domain.vo.SellerRegistrationVO;
import jakarta.validation.Valid;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-25
 */
public interface ISellerRegistrationService extends IService<SellerRegistration> {

    SellerRegistrationVO register(@Valid SellerRegistrationDTO sellerRegistrationDTO);

    SellerRegistrationVO getRegistrationInfo(Long userId);

    SellerRegistrationVO modifyReceiveAccount(Long userId, String verificationCode, String receivingAccount);

    void deleteSellerAccount(Long userId, String verificationCode);
}
