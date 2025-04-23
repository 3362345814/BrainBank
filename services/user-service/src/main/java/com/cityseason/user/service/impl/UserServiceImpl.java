package com.cityseason.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.common.util.RequestContext;
import com.cityseason.user.domain.dto.LoginDTO;
import com.cityseason.user.domain.dto.RegisterDTO;
import com.cityseason.user.domain.dto.UserDTO;
import com.cityseason.user.domain.enums.UserRole;
import com.cityseason.user.domain.enums.UserStatus;
import com.cityseason.user.domain.po.User;
import com.cityseason.user.domain.po.Wallet;
import com.cityseason.user.domain.query.UserQuery;
import com.cityseason.user.domain.vo.LoginVO;
import com.cityseason.user.domain.vo.UserVO;
import com.cityseason.user.mapper.UserMapper;
import com.cityseason.user.service.IUserService;
import com.cityseason.user.service.IWalletService;
import com.cityseason.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final IWalletService walletService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public UserVO register(RegisterDTO registerDTO) {
        // 1. 验证两次密码是否一致
        if (!Objects.equals(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 2. 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", registerDTO.getUsername());
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 3. 检查手机号是否已被注册
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", registerDTO.getPhone());
        count = this.count(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("手机号已被注册");
        }

        // 4. 检查邮箱是否已被注册(如果提供了邮箱)
        if (registerDTO.getEmail() != null && !registerDTO.getEmail().isEmpty()) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", registerDTO.getEmail());
            count = this.count(queryWrapper);
            if (count > 0) {
                throw new RuntimeException("邮箱已被注册");
            }
        }

        // 5. 创建用户实体
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        // 加密密码
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setRole(UserRole.ORDINARY);
        user.setStatus(UserStatus.NORMAL);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 6. 保存用户信息
        this.save(user);

        // 7. 为用户创建钱包
        Wallet wallet = new Wallet();
        wallet.setUserId(user.getId());
        walletService.save(wallet);

        // 8. 返回创建的用户
        return BeanUtil.copyProperties(user, UserVO.class);

    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 1. 通过用户名、邮箱或手机号查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, loginDTO.getAccount())
                .or()
                .eq(User::getPhone, loginDTO.getAccount());

        User user = getOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 检查用户状态
        if (UserStatus.FREEZE.equals(user.getStatus())) {
            throw new RuntimeException("账户已被冻结，请联系管理员");
        }

        // 3. 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 4. 更新最后登录时间
        user.setLastLoginAt(LocalDateTime.now());
        updateById(user);

        // 5. 生成JWT令牌
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 6. 查询用户钱包
        Wallet wallet = walletService.getWalletByUserId(user.getId());

        // 6.1. 如果钱包不存在，创建钱包
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUserId(user.getId());
            walletService.save(wallet);
        }

        // 7. 转成VO
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        // 8. 返回登录结果
        return new LoginVO(userVO, wallet, token);

    }

    @Override
    public UserVO updateUser(UserDTO userDTO) {
        // 1. 检查用户是否存在
        User user = getById(RequestContext.getCurrentUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 检查用户名是否已存在（如果要修改用户名）
        if (StringUtils.hasText(userDTO.getUsername()) && !userDTO.getUsername().equals(user.getUsername())) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername, userDTO.getUsername());
            long count = count(queryWrapper);
            if (count > 0) {
                throw new RuntimeException("用户名已存在");
            }
        }

        // 3. 检查邮箱是否已存在（如果要修改邮箱）
        if (StringUtils.hasText(userDTO.getEmail()) && !userDTO.getEmail().equals(user.getEmail())) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, userDTO.getEmail());
            long count = count(queryWrapper);
            if (count > 0) {
                throw new RuntimeException("邮箱已存在");
            }
        }

        // 4. 检查手机号是否已存在（如果要修改手机号）
        if (StringUtils.hasText(userDTO.getPhone()) && !userDTO.getPhone().equals(user.getPhone())) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, userDTO.getPhone());
            long count = count(queryWrapper);
            if (count > 0) {
                throw new RuntimeException("手机号已存在");
            }
        }

        // 5. 更新用户信息
        BeanUtil.copyProperties(userDTO, user);

        // 6. 设置更新时间
        user.setUpdatedAt(LocalDateTime.now());

        // 7. 保存更新
        updateById(user);

        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public PageDTO<UserVO> queryUserPage(UserQuery userQuery) {
        User user = getById(RequestContext.getCurrentUserId());
        // 检查用户是否是管理员
        if (!UserRole.ADMIN.equals(user.getRole())) {
            throw new RuntimeException("无权限");
        }
        Page<User> page = userQuery.toMpPage("last_login_at", false);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userQuery.getUsername()), User::getUsername, userQuery.getUsername())
                .eq(StringUtils.hasText(userQuery.getPhone()), User::getPhone, userQuery.getPhone())
                .eq(StringUtils.hasText(userQuery.getEmail()), User::getEmail, userQuery.getEmail())
                .eq(userQuery.getStatus() != null, User::getStatus, userQuery.getStatus())
                .eq(userQuery.getRole() != null, User::getRole, userQuery.getRole())
                .ge(userQuery.getLastLoginAtMin() != null, User::getLastLoginAt, userQuery.getLastLoginAtMin())
                .le(userQuery.getLastLoginAtMax() != null, User::getLastLoginAt, userQuery.getLastLoginAtMax());


        page(page, wrapper);

        return PageDTO.of(page, UserVO.class);
    }

    @Override
    public UserVO updateUserStatus(Long id, UserStatus status) {
        User requestUser = getById(RequestContext.getCurrentUserId());
        // 检查用户是否是管理员
        if (!UserRole.ADMIN.equals(requestUser.getRole())) {
            throw new RuntimeException("无权限");
        }
        // 检查用户是否存在
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 设置新的状态
        user.setStatus(status);
        user.setUpdatedAt(LocalDateTime.now());

        // 更新用户状态
        updateById(user);

        return BeanUtil.copyProperties(user, UserVO.class);
    }
}
