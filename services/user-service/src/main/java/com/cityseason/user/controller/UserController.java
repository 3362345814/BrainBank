package com.cityseason.user.controller;

import com.cityseason.common.domain.Result;
import com.cityseason.user.domain.dto.LoginDTO;
import com.cityseason.user.domain.dto.RegisterDTO;
import com.cityseason.user.domain.dto.UserDTO;
import com.cityseason.user.domain.vo.LoginVO;
import com.cityseason.user.domain.vo.UserVO;
import com.cityseason.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {

    private final IUserService userService;


    /**
     * 用户注册
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            log.info("用户注册请求: {}", registerDTO.getUsername());
            UserVO userVO = userService.register(registerDTO);
            return Result.success(userVO);
        } catch (Exception e) {
            log.error("用户注册失败: {}", e.getMessage(), e);
            return Result.failure(400, e.getMessage());
        }
    }


    /**
     * 用户登录
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            LoginVO loginVO = userService.login(loginDTO);
            return Result.success(loginVO);
        } catch (Exception e) {
            log.error("用户登录失败: {}", e.getMessage(), e);
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息")
    @PutMapping("/update")
    public Result<UserVO> updateUser(@Valid @RequestBody UserDTO userDTO,
                                     @RequestHeader("X-User-Id") Long userId) {

        log.info("更新用户信息请求: userId={}, userDTO={}", userId, userDTO);
        try {
            UserVO userVO = userService.updateUser(userDTO, userId);
            return Result.success(userVO);
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            return Result.failure(400, e.getMessage());
        }
    }


}
