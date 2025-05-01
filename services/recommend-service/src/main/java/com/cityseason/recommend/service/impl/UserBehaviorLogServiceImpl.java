package com.cityseason.recommend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.cityseason.api.client.ContentClient;
import com.cityseason.api.domain.vo.ContentTagVO;
import com.cityseason.api.domain.vo.Result;
import com.cityseason.common.util.RequestContext;
import com.cityseason.recommend.domain.dto.UserBehaviorLogDTO;
import com.cityseason.recommend.domain.po.UserBehaviorLog;
import com.cityseason.recommend.domain.po.UserInterestProfile;
import com.cityseason.recommend.domain.vo.UserBehaviorLogVO;
import com.cityseason.recommend.mapper.UserBehaviorLogMapper;
import com.cityseason.recommend.service.IUserBehaviorLogService;
import com.cityseason.recommend.service.IUserInterestProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
@RequiredArgsConstructor
@Slf4j
public class UserBehaviorLogServiceImpl extends ServiceImpl<UserBehaviorLogMapper, UserBehaviorLog> implements IUserBehaviorLogService {

    private final ContentClient contentClient;

    private final IUserInterestProfileService userInterestProfileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserBehaviorLogVO record(UserBehaviorLogDTO userBehaviorLogDTO) {
        if (!userBehaviorLogDTO.getUserId().equals(RequestContext.getCurrentUserId())) {
            throw new RuntimeException("无权限");
        }

        UserBehaviorLog userBehaviorLog = BeanUtil.copyProperties(userBehaviorLogDTO, UserBehaviorLog.class);

        save(userBehaviorLog);

        // 使用contentId查询对应的标签
        Result<List<ContentTagVO>> result = contentClient.selectByContentId(userBehaviorLogDTO.getContentId());

        List<Long> tagIds = result.getData().stream().map(ContentTagVO::getTagId).toList();

        if (tagIds.isEmpty()) {
            return BeanUtil.copyProperties(userBehaviorLog, UserBehaviorLogVO.class);
        }

        // 查询数据库中是否存在该用户的兴趣画像
        for (Long tagId : tagIds) {
            UserInterestProfile userInterestProfile = userInterestProfileService.getOne(
                    new LambdaQueryWrapper<UserInterestProfile>()
                            .eq(UserInterestProfile::getUserId, userBehaviorLogDTO.getUserId())
                            .eq(UserInterestProfile::getTagId, tagId)
            );

            if (userInterestProfile == null) {
                // 如果不存在，则创建一个新的兴趣画像
                UserInterestProfile newUserInterestProfile = new UserInterestProfile();
                newUserInterestProfile.setUserId(userBehaviorLogDTO.getUserId());
                newUserInterestProfile.setTagId(tagId);
                newUserInterestProfile.setPreference(BigDecimal.valueOf(userBehaviorLogDTO.getBehaviorType().value));
                Db.save(newUserInterestProfile);
            } else {
                // 如果存在，则更新兴趣画像的偏好强度，最高为1
                BigDecimal preference = userInterestProfile.getPreference();
                preference = preference.multiply(BigDecimal.valueOf(userBehaviorLogDTO.getBehaviorType().value + 1));
                if (preference.compareTo(BigDecimal.valueOf(1)) > 0) {
                    preference = BigDecimal.valueOf(1);
                }
                userInterestProfile.setPreference(preference);
                Db.updateById(userInterestProfile);
            }
        }
        // 找到不含tagId的兴趣画像，将其偏好强度降低
        List<UserInterestProfile> userInterestProfiles = userInterestProfileService.list(
                new LambdaQueryWrapper<UserInterestProfile>()
                        .eq(UserInterestProfile::getUserId, userBehaviorLogDTO.getUserId())
                        .notIn(UserInterestProfile::getTagId, tagIds)
        );
        for (UserInterestProfile userInterestProfile : userInterestProfiles) {
            BigDecimal preference = userInterestProfile.getPreference();
            preference = preference.multiply(BigDecimal.valueOf(1 - userBehaviorLogDTO.getBehaviorType().value));
            if (preference.compareTo(BigDecimal.valueOf(0)) < 0) {
                preference = BigDecimal.valueOf(0);
            }
            userInterestProfile.setPreference(preference);
            Db.updateById(userInterestProfile);
        }

        return BeanUtil.copyProperties(userBehaviorLog, UserBehaviorLogVO.class);
    }
}
