package com.cityseason.user.service.impl;

import com.cityseason.user.domain.po.User;
import com.cityseason.user.mapper.UserMapper;
import com.cityseason.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
