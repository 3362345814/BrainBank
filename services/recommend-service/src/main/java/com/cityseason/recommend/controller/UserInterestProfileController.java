package com.cityseason.recommend.controller;


import com.cityseason.api.domain.vo.Result;
import com.cityseason.log.annotation.OperationLog;
import com.cityseason.recommend.domain.vo.UserInterestProfileVO;
import com.cityseason.recommend.service.IUserInterestProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户兴趣画像
 *
 * @author 林心海
 * @since 2025-04-30
 */
@RestController
@RequestMapping("/recommend/user-interest-profile")
@RequiredArgsConstructor
public class UserInterestProfileController {

    private final IUserInterestProfileService userInterestProfileService;


    /**
     * 手动生成用户兴趣画像
     *
     * @param userId 用户ID
     * @param tagIds 标签ID列表
     * @return 用户兴趣画像
     */
    @PostMapping("/generate-by-hand")
    @OperationLog(module = "用户兴趣画像", operation = "手动生成用户兴趣画像")
    public Result<List<UserInterestProfileVO>> generateByHand(@RequestParam Long userId, @RequestParam Long[] tagIds) {

        try {
            List<UserInterestProfileVO> profiles = userInterestProfileService.generateByHand(userId, tagIds);
            return Result.success(profiles);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 获取用户兴趣画像
     *
     * @param userId 用户ID
     * @return 用户兴趣画像
     */
    @GetMapping("/get")
    @OperationLog(module = "用户兴趣画像", operation = "获取用户兴趣画像")
    public Result<List<UserInterestProfileVO>> getByUserId(@RequestParam Long userId) {
        try {
            List<UserInterestProfileVO> profiles = userInterestProfileService.getByUserId(userId);
            return Result.success(profiles);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }
}
