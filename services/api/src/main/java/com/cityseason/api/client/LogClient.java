package com.cityseason.api.client;


import com.cityseason.api.domain.po.ApiAccessLog;
import com.cityseason.api.domain.po.ErrorLog;
import com.cityseason.api.domain.po.LoginLog;
import com.cityseason.api.domain.po.OperationLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志服务Feign客户端接口
 */
@FeignClient(name = "log-service", fallbackFactory = LogClientFallbackFactory.class)
public interface LogClient {
    /**
     * 保存API访问日志
     */
    @PostMapping("/log/access")
    void saveApiAccessLog(@RequestBody ApiAccessLog apiAccessLog);

    /**
     * 保存错误日志
     */
    @PostMapping("/log/error")
    void saveErrorLog(@RequestBody ErrorLog errorLog);

    /**
     * 保存登录日志
     */
    @PostMapping("/log/login")
    void saveLoginLog(@RequestBody LoginLog loginLog);

    /**
     * 保存操作日志
     */
    @PostMapping("/log/operation")
    void saveOperationLog(@RequestBody OperationLog operationLog);
}