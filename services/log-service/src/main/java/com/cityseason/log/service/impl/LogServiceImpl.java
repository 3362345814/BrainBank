package com.cityseason.log.service.impl;

import com.cityseason.api.domain.po.ApiAccessLog;
import com.cityseason.api.domain.po.ErrorLog;
import com.cityseason.api.domain.po.LoginLog;
import com.cityseason.api.domain.po.OperationLog;
import com.cityseason.log.mapper.ApiAccessLogMapper;
import com.cityseason.log.mapper.ErrorLogMapper;
import com.cityseason.log.mapper.LoginLogMapper;
import com.cityseason.log.mapper.OperationLogMapper;
import com.cityseason.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 日志服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {
    private final ApiAccessLogMapper apiAccessLogMapper;
    private final ErrorLogMapper errorLogMapper;
    private final LoginLogMapper loginLogMapper;
    private final OperationLogMapper operationLogMapper;

    /**
     * 异步保存API访问日志
     */
    @Async
    @Override
    public void saveApiAccessLog(ApiAccessLog apiAccessLog) {
        try {
            apiAccessLogMapper.insert(apiAccessLog);
        } catch (Exception e) {
            log.error("Failed to save API access log", e);
        }
    }

    /**
     * 异步保存错误日志
     */
    @Async
    @Override
    public void saveErrorLog(ErrorLog errorLog) {
        try {
            errorLogMapper.insert(errorLog);
        } catch (Exception e) {
            log.error("Failed to save error log", e);
        }
    }

    /**
     * 异步保存登录日志
     */
    @Async
    @Override
    public void saveLoginLog(LoginLog loginLog) {
        try {
            loginLogMapper.insert(loginLog);
        } catch (Exception e) {
            log.error("Failed to save login log", e);
        }
    }

    /**
     * 异步保存操作日志
     */
    @Async
    @Override
    public void saveOperationLog(OperationLog operationLog) {
        try {
            operationLogMapper.insert(operationLog);
        } catch (Exception e) {
            log.error("Failed to save operation log", e);
        }
    }
}