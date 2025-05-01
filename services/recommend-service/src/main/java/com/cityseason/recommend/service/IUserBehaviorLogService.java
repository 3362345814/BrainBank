package com.cityseason.recommend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.recommend.domain.dto.UserBehaviorLogDTO;
import com.cityseason.recommend.domain.po.UserBehaviorLog;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-30
 */
public interface IUserBehaviorLogService extends IService<UserBehaviorLog> {

    UserBehaviorLog record(UserBehaviorLogDTO userBehaviorLogDTO);
}
