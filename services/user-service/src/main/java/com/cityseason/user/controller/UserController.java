package com.cityseason.user.controller;

import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.common.domain.vo.Result;
import com.cityseason.log.annotation.OperationLog;
import com.cityseason.log.client.LogClient;
import com.cityseason.log.domain.po.LoginLog;
import com.cityseason.user.domain.dto.LoginDTO;
import com.cityseason.user.domain.dto.RegisterDTO;
import com.cityseason.user.domain.dto.UserDTO;
import com.cityseason.user.domain.enums.UserStatus;
import com.cityseason.user.domain.query.UserQuery;
import com.cityseason.user.domain.vo.LoginVO;
import com.cityseason.user.domain.vo.UserVO;
import com.cityseason.user.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 用户管理
 *
 * @author 林心海
 * @since 2025-04-19
 */
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final LogClient logClient;
    private final HttpServletRequest request;


    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     * @return 用户信息
     */
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody RegisterDTO registerDTO) {

        try {
            UserVO userVO = userService.register(registerDTO);
            return Result.success(userVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }


    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录用户信息
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            LoginVO loginVO = userService.login(loginDTO);

            // 手动记录登录日志
            LoginLog loginLog = new LoginLog();
            loginLog.setUserId(loginVO.getUserVO().getId());
            loginLog.setUsername(loginVO.getUserVO().getUsername());
            loginLog.setIpAddress(request.getRemoteAddr());
            loginLog.setStatus(1); // 登录成功
            loginLog.setLoginTime(LocalDateTime.now());

            // 异步记录登录日志
            logClient.saveLoginLog(loginLog);

            return Result.success(loginVO);
        } catch (Exception e) {

            // 登录失败也记录日志
            LoginLog loginLog = new LoginLog();
            loginLog.setStatus(0); // 登录失败
            loginLog.setMsg(e.getMessage());
            loginLog.setIpAddress(request.getRemoteAddr());
            loginLog.setLoginTime(LocalDateTime.now());

            try {
                logClient.saveLoginLog(loginLog);
            } catch (Exception ex) {
                log.error("Failed to save login log", ex);
            }

            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 更新用户信息
     *
     * @param userDTO 用户信息
     * @return 用户信息
     */
    @PutMapping("/update")
    @OperationLog(module = "用户管理", operation = "更新用户信息")
    public Result<UserVO> updateUser(@Valid @RequestBody UserDTO userDTO) {

        try {
            UserVO userVO = userService.updateUser(userDTO);
            return Result.success(userVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 查询用户列表
     *
     * @param userQuery 用户查询条件
     * @return 用户列表
     */
    @GetMapping("/page")
    @OperationLog(module = "用户管理", operation = "查询用户分页")
    public Result<PageDTO<UserVO>> queryUserPage(UserQuery userQuery) {

        try {
            PageDTO<UserVO> pageDTO = userService.queryUserPage(userQuery);
            return Result.success(pageDTO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 更新用户状态
     *
     * @param id     用户ID
     * @param status 用户状态
     * @return 用户信息
     */
    @PutMapping("/status")
    @OperationLog(module = "用户管理", operation = "冻结/解冻用户")
    public Result<UserVO> updateUserStatus(@RequestParam Long id, @RequestParam Integer status) {

        try {
            UserVO userVO = userService.updateUserStatus(id, UserStatus.fromCode(status));
            return Result.success(userVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }

    }


}
