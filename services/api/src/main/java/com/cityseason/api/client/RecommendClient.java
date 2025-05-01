package com.cityseason.api.client;

import com.cityseason.api.domain.dto.UserBehaviorLogDTO;
import com.cityseason.api.domain.vo.Result;
import com.cityseason.api.domain.vo.UserBehaviorLogVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "recommend-service")
public interface RecommendClient {
    @PostMapping("/recommend/user-behavior-log/record")
    Result<UserBehaviorLogVO> record(@RequestBody UserBehaviorLogDTO UserBehaviorLogDTO);
}
