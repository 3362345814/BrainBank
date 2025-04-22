package com.cityseason.user.controller;

import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.common.domain.vo.Result;
import com.cityseason.user.domain.dto.LoginDTO;
import com.cityseason.user.domain.dto.RegisterDTO;
import com.cityseason.user.domain.dto.UserDTO;
import com.cityseason.user.domain.enums.UserStatus;
import com.cityseason.user.domain.query.UserQuery;
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

        log.info("用户注册请求: {}", registerDTO.getUsername());
        try {
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

        log.info("用户登录请求: {}", loginDTO);
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
    public Result<UserVO> updateUser(@Valid @RequestBody UserDTO userDTO) {

        log.info("更新用户信息请求: {}", userDTO);
        try {
            UserVO userVO = userService.updateUser(userDTO);
            return Result.success(userVO);
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            return Result.failure(400, e.getMessage());
        }
    }

    @Operation(summary = "分页查询用户信息")
    @GetMapping("/page")
    public Result<PageDTO<UserVO>> queryUserPage(UserQuery userQuery) {

        log.info("分页查询用户信息请求: {}", userQuery);
        try {
            PageDTO<UserVO> pageDTO = userService.queryUserPage(userQuery);
            return Result.success(pageDTO);
        } catch (Exception e) {
            log.error("分页查询用户信息失败", e);
            return Result.failure(400, e.getMessage());
        }
    }

    @Operation(summary = "冻结/解冻用户")
    @PutMapping("/status")
    public Result<UserVO> updateUserStatus(@RequestParam Long id, @RequestParam Integer status) {

        log.info("冻结/解冻用户请求: id={}, status={}", id, status);
        try {
            UserVO userVO = userService.updateUserStatus(id, UserStatus.fromCode(status));
            return Result.success(userVO);
        } catch (Exception e) {
            log.error("冻结/解冻用户失败", e);
            return Result.failure(400, e.getMessage());
        }

    }


}
