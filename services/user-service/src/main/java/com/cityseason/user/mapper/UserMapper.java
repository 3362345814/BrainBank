package com.cityseason.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cityseason.user.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
