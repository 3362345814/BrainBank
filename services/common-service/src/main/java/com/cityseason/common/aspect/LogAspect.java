package com.cityseason.common.aspect;

import com.cityseason.log.annotation.OperationLog;
import com.cityseason.log.client.LogClient;
import com.cityseason.log.domain.po.ApiAccessLog;
import com.cityseason.log.domain.po.ErrorLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 用户服务日志切面类，用于记录API访问日志和操作日志
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LogAspect {
    private final ObjectMapper objectMapper;
    private final LogClient logClient;

    /**
     * 定义切点，拦截controller包下的所有方法
     */
    @Pointcut("execution(public * com.cityseason.*.controller.*.*(..))")
    public void controllerLog() {
    }

    /**
     * 定义切点，拦截有OperationLog注解的方法
     */
    @Pointcut("@annotation(com.cityseason.log.annotation.OperationLog)")
    public void operationLog() {
    }

    /**
     * 环绕通知，记录API访问日志
     */
    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 创建API访问日志对象
        ApiAccessLog apiAccessLog = new ApiAccessLog();

        apiAccessLog.setRequestUrl(request.getRequestURI());
        apiAccessLog.setRequestMethod(request.getMethod());
        apiAccessLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        apiAccessLog.setIpAddress(getIpAddress(request));

        try {
            apiAccessLog.setRequestParam(objectMapper.writeValueAsString(joinPoint.getArgs()));
        } catch (Exception e) {
            apiAccessLog.setRequestParam("Failed to serialize request parameters: " + e.getMessage());
            log.error("Failed to serialize request parameters", e);
        }

        apiAccessLog.setCreateTime(LocalDateTime.now());

        Object result;
        try {
            // 执行目标方法
            result = joinPoint.proceed();

            // 记录返回结果
            try {
                apiAccessLog.setResponseData(objectMapper.writeValueAsString(result));
            } catch (Exception e) {
                apiAccessLog.setResponseData("Failed to serialize response: " + e.getMessage());
                log.error("Failed to serialize response", e);
            }

            apiAccessLog.setStatusCode(200);
        } catch (Exception e) {
            // 记录异常信息
            apiAccessLog.setStatusCode(500);
            apiAccessLog.setResponseData(e.getMessage());

            // 记录错误日志
            ErrorLog errorLog = new ErrorLog();
            errorLog.setClassName(joinPoint.getSignature().getDeclaringTypeName());
            errorLog.setMethodName(joinPoint.getSignature().getName());
            errorLog.setThreadName(Thread.currentThread().getName());
            errorLog.setMessage(e.getMessage());
            errorLog.setStackTrace(Arrays.toString(e.getStackTrace()));
            errorLog.setOccurredAt(LocalDateTime.now());

            try {
                logClient.saveErrorLog(errorLog);
            } catch (Exception ex) {
                log.error("Failed to save error log", ex);
            }

            throw e;
        } finally {
            // 计算响应时间
            long endTime = System.currentTimeMillis();
            apiAccessLog.setResponseTime((int) (endTime - startTime));

            // 保存API访问日志
            try {
                logClient.saveApiAccessLog(apiAccessLog);
            } catch (Exception e) {
                log.error("Failed to save API access log", e);
            }
        }

        return result;
    }

    /**
     * 后置通知，记录操作日志
     */
    @AfterReturning(pointcut = "operationLog()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }

            HttpServletRequest request = attributes.getRequest();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            // 从自定义注解中获取操作描述信息
            OperationLog annotation = method.getAnnotation(OperationLog.class);

            // 创建操作日志对象
            com.cityseason.log.domain.po.OperationLog operationLog = new com.cityseason.log.domain.po.OperationLog();

            // 从请求头或会话中获取用户信息
            String userId = request.getHeader("x-user-id");
            if (userId != null) {
                operationLog.setUserId(Long.parseLong(userId));
            }

            operationLog.setModule(annotation.module());
            operationLog.setOperation(annotation.operation());
            operationLog.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            operationLog.setRequestUrl(request.getRequestURI());
            operationLog.setRequestType(request.getMethod());
            operationLog.setIpAddress(getIpAddress(request));
            operationLog.setUserAgent(request.getHeader("User-Agent"));

            try {
                operationLog.setRequestParam(objectMapper.writeValueAsString(joinPoint.getArgs()));
                operationLog.setResponseData(objectMapper.writeValueAsString(result));
            } catch (Exception e) {
                log.error("Failed to serialize request or response data", e);
            }

            operationLog.setStatus(1); // 正常状态
            operationLog.setCreateTime(LocalDateTime.now());

            // 保存操作日志
            logClient.saveOperationLog(operationLog);
            log.info("Saved operation log for {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        } catch (Exception e) {
            log.error("Failed to save operation log", e);
        }
    }

    /**
     * 获取客户端真实IP
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}