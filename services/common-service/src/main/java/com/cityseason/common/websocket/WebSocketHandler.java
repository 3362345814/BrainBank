package com.cityseason.common.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // 存放所有连接
    private static final ConcurrentHashMap<Long, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = getUserId(session);
        if (userId != null) {
            sessionMap.put(userId, session);
            log.info("用户连接: userId={}, 当前在线人数={}", userId, sessionMap.size());
        } else {
            log.warn("连接缺少userId参数，关闭连接");
            session.close();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long userId = getUserId(session);
        log.info("收到来自 userId={} 的消息: {}", userId, message.getPayload());
        // 这里可以处理客户端发来的消息，比如心跳包
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = getUserId(session);
        sessionMap.remove(userId);
        log.info("用户断开连接: userId={}, 当前在线人数={}", userId, sessionMap.size());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Long userId = getUserId(session);
        log.error("WebSocket错误: userId={}, error={}", userId, exception.getMessage());
        session.close();
    }

    // 主动推送单个用户
    public static void sendToUser(Long userId, String message) {
        WebSocketSession session = sessionMap.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                log.error("推送消息失败，userId={}", userId, e);
            }
        }
    }

    // 广播所有用户
    public static void broadcast(String message) {
        sessionMap.forEach((userId, session) -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    log.error("广播消息失败，userId={}", userId, e);
                }
            }
        });
    }

    // 获取userId
    private Long getUserId(WebSocketSession session) {
        try {
            String uri = session.getUri().toString();
            if (uri.contains("/ws/")) {
                String userIdStr = uri.substring(uri.lastIndexOf("/ws/") + 4);
                return Long.valueOf(userIdStr);
            }
        } catch (Exception e) {
            log.warn("解析userId失败", e);
        }
        return null;
    }
}