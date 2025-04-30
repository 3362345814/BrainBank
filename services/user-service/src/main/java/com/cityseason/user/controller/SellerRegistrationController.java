package com.cityseason.user.controller;


import com.cityseason.api.domin.vo.Result;
import com.cityseason.log.annotation.OperationLog;
import com.cityseason.user.domain.dto.SellerRegistrationDTO;
import com.cityseason.user.domain.vo.SellerRegistrationVO;
import com.cityseason.user.service.ISellerRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 商户管理
 *
 * @author 林心海
 * @since 2025-04-25
 */
@RestController
@RequestMapping("/user/seller-registration")
@RequiredArgsConstructor
@Slf4j
public class SellerRegistrationController {


    private final ISellerRegistrationService sellerRegistrationService;

    /**
     * 商户注册
     *
     * @param sellerRegistrationDTO 注册信息
     * @return 注册信息
     */
    @PostMapping("/register")
    @OperationLog(module = "商户管理", operation = "商户注册")
    public Result<SellerRegistrationVO> register(@Valid @RequestBody SellerRegistrationDTO sellerRegistrationDTO) {

        try {
            SellerRegistrationVO sellerRegistrationVO = sellerRegistrationService.register(sellerRegistrationDTO);
            return Result.success(sellerRegistrationVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 查询注册信息
     *
     * @param userId 用户id
     * @return 注册信息
     */
    @GetMapping("/get")
    @OperationLog(module = "商户管理", operation = "查询注册信息")
    public Result<SellerRegistrationVO> getRegistrationInfo(@RequestParam Long userId) {
        try {
            SellerRegistrationVO sellerRegistrationVO = sellerRegistrationService.getRegistrationInfo(userId);
            return Result.success(sellerRegistrationVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 修改收入账户
     *
     * @param userId           用户id
     * @param verificationCode 验证码
     * @param receivingAccount 新收款账户
     * @return 注册信息
     */
    @PostMapping("/modify-receive-account")
    @OperationLog(module = "商户管理", operation = "修改收入账户")
    public Result<SellerRegistrationVO> modifyReceiveAccount(@RequestParam Long userId, @RequestParam String verificationCode, @RequestParam String receivingAccount) {
        try {
            SellerRegistrationVO sellerRegistrationVO = sellerRegistrationService.modifyReceiveAccount(userId, verificationCode, receivingAccount);
            return Result.success(sellerRegistrationVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }


    /**
     * 注销商家账户
     *
     * @param userId           用户id
     * @param verificationCode 验证码
     * @return 注销结果
     */
    @DeleteMapping("/delete")
    @OperationLog(module = "商户管理", operation = "注销商家账户")
    public Result<Boolean> deleteSellerAccount(@RequestParam Long userId, @RequestParam String verificationCode) {
        try {
            sellerRegistrationService.deleteSellerAccount(userId, verificationCode);
            return Result.success(true);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

}
