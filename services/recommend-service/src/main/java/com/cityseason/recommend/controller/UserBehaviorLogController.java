package com.cityseason.recommend.controller;


import com.cityseason.api.domain.vo.Result;
import com.cityseason.log.annotation.OperationLog;
import com.cityseason.recommend.domain.dto.UserBehaviorLogDTO;
import com.cityseason.recommend.domain.po.UserBehaviorLog;
import com.cityseason.recommend.service.IUserBehaviorLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户行为日志
 *
 * @author 林心海
 * @since 2025-04-30
 */
@RestController
@RequestMapping("/recommend/user-behavior-log")
@RequiredArgsConstructor
@Slf4j
public class UserBehaviorLogController {

    private final IUserBehaviorLogService userBehaviorLogService;

    /**
     * 记录用户行为
     *
     * @param UserBehaviorLogDTO 用户行为日志DTO
     * @return 用户行为日志
     */
    @PostMapping("/record")
    @OperationLog(module = "用户行为日志", operation = "记录用户行为")
    public Result<UserBehaviorLog> record(@RequestBody UserBehaviorLogDTO UserBehaviorLogDTO) {
        try {
            UserBehaviorLog userBehaviorLog = userBehaviorLogService.record(UserBehaviorLogDTO);
            return Result.success(userBehaviorLog);
        } catch (Exception e) {

            return Result.failure(400, e.getMessage());
        }
    }
}
