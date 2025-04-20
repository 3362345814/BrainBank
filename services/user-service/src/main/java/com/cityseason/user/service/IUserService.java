package com.cityseason.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.user.domain.dto.LoginDTO;
import com.cityseason.user.domain.dto.RegisterDTO;
import com.cityseason.user.domain.dto.UserDTO;
import com.cityseason.user.domain.po.User;
import com.cityseason.user.domain.vo.LoginVO;
import com.cityseason.user.domain.vo.UserVO;
import jakarta.validation.Valid;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
public interface IUserService extends IService<User> {
    UserVO register(RegisterDTO registerDTO);

    LoginVO login(LoginDTO loginDTO);

    UserVO updateUser(@Valid UserDTO userDTO, Long userId);
}