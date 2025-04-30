package com.cityseason.api.client;

import com.cityseason.api.domin.po.ApiAccessLog;
import com.cityseason.api.domin.po.ErrorLog;
import com.cityseason.api.domin.po.LoginLog;
import com.cityseason.api.domin.po.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 日志客户端降级工厂
 */
@Component
@Slf4j
public class LogClientFallbackFactory implements FallbackFactory<LogClient> {
    @Override
    public LogClient create(Throwable cause) {
        return new LogClient() {
            @Override
            public void saveApiAccessLog(ApiAccessLog apiAccessLog) {
                log.error("Failed to save API access log: {}", cause.getMessage());
            }

            @Override
            public void saveErrorLog(ErrorLog errorLog) {
                log.error("Failed to save error log: {}", cause.getMessage());
            }

            @Override
            public void saveLoginLog(LoginLog loginLog) {
                log.error("Failed to save login log: {}", cause.getMessage());
            }

            @Override
            public void saveOperationLog(OperationLog operationLog) {
                log.error("Failed to save operation log: {}", cause.getMessage());
            }
        };
    }
}