package com.cityseason.recommend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cityseason.common.util.RequestContext;
import com.cityseason.recommend.domain.po.UserInterestProfile;
import com.cityseason.recommend.domain.vo.UserInterestProfileVO;
import com.cityseason.recommend.mapper.UserInterestProfileMapper;
import com.cityseason.recommend.service.IUserInterestProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-30
 */
@Service
public class UserInterestProfileServiceImpl extends ServiceImpl<UserInterestProfileMapper, UserInterestProfile> implements IUserInterestProfileService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserInterestProfileVO> generateByHand(Long userId, Long[] tagIds) {
        if (!userId.equals(RequestContext.getCurrentUserId())) {
            throw new RuntimeException("无权限");
        }

        List<UserInterestProfile> profiles = new ArrayList<>();

        for (Long tagId : tagIds) {
            UserInterestProfile profile = new UserInterestProfile();
            profile.setUserId(userId);
            // 默认偏好强度为0.5
            profile.setPreference(BigDecimal.valueOf(0.5));
            profile.setTagId(tagId);
            profiles.add(profile);
        }
        // 批量插入
        saveBatch(profiles);

        return BeanUtil.copyToList(profiles, UserInterestProfileVO.class);
    }

    @Override
    public List<UserInterestProfileVO> getByUserId(Long userId) {
        // 使用UserId查询
        return BeanUtil.copyToList(list(
                new LambdaQueryWrapper<UserInterestProfile>()
                        .eq(UserInterestProfile::getUserId, userId)
        ), UserInterestProfileVO.class);
    }
}
