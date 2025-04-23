package com.cityseason.content.interceptor;

import com.cityseason.common.util.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Slf4j
@Component
public class RequestHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 初始化上下文
        RequestContext.init();

        // 获取所有请求头信息
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);

            // 选择性地保存某些请求头，比如认证信息、用户ID等
            if (headerName.equalsIgnoreCase("Authorization") ||
                    headerName.equalsIgnoreCase("X-User-Id")) {
                RequestContext.set(headerName, headerValue);
                log.info("Header {}: {}", headerName, headerValue);
            }
        }


        return true; // 返回true表示继续处理请求
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求处理完成后清除上下文，防止内存泄漏
        RequestContext.remove();
    }
}