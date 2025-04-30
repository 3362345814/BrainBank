package com.cityseason.log.controller;

import com.cityseason.api.domin.po.ApiAccessLog;
import com.cityseason.api.domin.po.ErrorLog;
import com.cityseason.api.domin.po.LoginLog;
import com.cityseason.api.domin.po.OperationLog;
import com.cityseason.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制器
 */
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
@Slf4j
public class LogController {
    private final LogService logService;

    /**
     * 保存API访问日志
     */
    @PostMapping("/access")
    public void saveApiAccessLog(@RequestBody ApiAccessLog apiAccessLog) {
        log.info("Received API access log: {}", apiAccessLog);
        logService.saveApiAccessLog(apiAccessLog);
    }

    /**
     * 保存错误日志
     */
    @PostMapping("/error")
    public void saveErrorLog(@RequestBody ErrorLog errorLog) {
        log.info("Received error log: {}", errorLog);
        logService.saveErrorLog(errorLog);
    }

    /**
     * 保存登录日志
     */
    @PostMapping("/login")
    public void saveLoginLog(@RequestBody LoginLog loginLog) {
        log.info("Received login log: {}", loginLog);
        logService.saveLoginLog(loginLog);
    }

    /**
     * 保存操作日志
     */
    @PostMapping("/operation")
    public void saveOperationLog(@RequestBody OperationLog operationLog) {
        log.info("Received operation log: {}", operationLog);
        logService.saveOperationLog(operationLog);
    }
}