package com.cityseason.recommend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.recommend.domain.po.UserInterestProfile;
import com.cityseason.recommend.domain.vo.UserInterestProfileVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-30
 */
public interface IUserInterestProfileService extends IService<UserInterestProfile> {

    List<UserInterestProfileVO> generateByHand(Long userId, Long[] tagIds);

    List<UserInterestProfileVO> getByUserId(Long userId);
}
