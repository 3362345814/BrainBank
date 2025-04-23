package com.cityseason.log.service;

import com.cityseason.log.domain.po.ApiAccessLog;
import com.cityseason.log.domain.po.ErrorLog;
import com.cityseason.log.domain.po.LoginLog;
import com.cityseason.log.domain.po.OperationLog;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-23
 */
public interface LogService {
    void saveApiAccessLog(ApiAccessLog apiAccessLog);

    void saveErrorLog(ErrorLog errorLog);

    void saveLoginLog(LoginLog loginLog);

    void saveOperationLog(OperationLog operationLog);

}
