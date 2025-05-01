package com.cityseason.api.client;

import com.cityseason.api.domain.vo.ContentTagVO;
import com.cityseason.api.domain.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "content-service")
public interface ContentClient {

    @GetMapping("/content/content-tag/select-by-content")
    Result<List<ContentTagVO>> selectByContentId(@RequestParam Long contentId);
}
