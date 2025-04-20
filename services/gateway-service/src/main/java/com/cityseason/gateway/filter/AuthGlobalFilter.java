package com.cityseason.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret:BrainBankSecretKey}")
    private String secret;

    // 定义不需要验证的路径
    private static final Set<String> WHITE_LIST = new HashSet<>();

    static {
        // 登录注册接口不需要验证
        WHITE_LIST.add("/user/login");
        WHITE_LIST.add("/user/register");

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 如果是白名单中的接口，直接放行
        if (isWhiteList(path)) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            return unauthorized(exchange, "未授权，请先登录");
        }

        // 如果以Bearer开头，去掉Bearer
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            // 验证token
            boolean verify = JWTUtil.verify(token, secret.getBytes());
            if (!verify) {
                return unauthorized(exchange, "无效的token");
            }

            // 解析token
            JWT jwt = JWTUtil.parseToken(token);

            // 验证是否过期
            boolean verifyTime = jwt.setKey(secret.getBytes()).validate(0);
            if (!verifyTime) {
                return unauthorized(exchange, "token已过期");
            }

            // 获取用户ID
            Long userId = jwt.getPayload().getClaimsJson().getLong("userId", -1L);
            if (userId == -1L) {
                return unauthorized(exchange, "无效的token");
            }

            // 获取用户名
            String username = jwt.getPayload().getClaimsJson().getStr("username");

            // 将用户信息传递给下游服务
            ServerHttpRequest newRequest = request.mutate()
                    .header("X-User-Id", userId.toString())
                    .header("X-Username", username)
                    .build();

            // 使用新的请求头继续过滤链
            log.info("token验证通过，用户ID：{}，用户名：{}", userId, username);
            return chain.filter(exchange.mutate().request(newRequest).build());
        } catch (Exception e) {
            log.error("验证token失败：", e);
            return unauthorized(exchange, "验证token失败");
        }
    }

    private boolean isWhiteList(String path) {
        return WHITE_LIST.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 构建错误响应
        String body = String.format("{\"code\":401,\"message\":\"%s\"}", message);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序，值越小优先级越高
        return 0;
    }
}
