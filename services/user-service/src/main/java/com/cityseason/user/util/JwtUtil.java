package com.cityseason.user.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * 密钥
     */
    @Value("${jwt.secret:BrainBankSecretKey}")
    private String secret;

    /**
     * 过期时间（单位：秒）
     * 默认24小时
     */
    @Value("${jwt.expiration:86400}")
    private int expiration;

    /**
     * 生成JWT令牌
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateToken(Long userId, String username) {
        return generateToken(userId, username, new HashMap<>());
    }

    /**
     * 生成JWT令牌（带额外信息）
     *
     * @param userId      用户ID
     * @param username    用户名
     * @param extraClaims 额外信息
     * @return JWT令牌
     */
    public String generateToken(Long userId, String username, Map<String, Object> extraClaims) {
        // 当前时间
        DateTime now = DateTime.now();
        // 过期时间
        DateTime expireTime = now.offsetNew(DateField.SECOND, expiration);

        // 创建payload
        Map<String, Object> payload = new HashMap<>(extraClaims);

        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expireTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 用户ID
        payload.put("userId", userId);
        // 用户名
        payload.put("username", username);

        // 创建签名器
        JWTSigner signer = JWTSignerUtil.hs256(secret.getBytes());

        // 生成JWT令牌
        return JWTUtil.createToken(payload, signer);
    }

    /**
     * 解析JWT令牌
     *
     * @param token JWT令牌
     * @return JWT对象
     */
    public JWT parseToken(String token) {
        return JWTUtil.parseToken(token);
    }

    /**
     * 验证JWT令牌
     *
     * @param token JWT令牌
     * @return 是否有效
     */
    public boolean verifyToken(String token) {
        if (StrUtil.isBlank(token)) {
            return false;
        }

        try {
            JWT jwt = parseToken(token);
            // 验证签名
            boolean verifyKey = jwt.setKey(secret.getBytes()).verify();
            // 验证过期时间
            boolean verifyTime = jwt.validate(0);

            return verifyKey && verifyTime;
        } catch (Exception e) {
            log.error("JWT令牌验证失败：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 从JWT令牌中获取用户ID
     *
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        if (!verifyToken(token)) {
            return null;
        }

        try {
            JWT jwt = parseToken(token);
            return jwt.getPayload().getClaimsJson().getLong("userId", -1L);
        } catch (Exception e) {
            log.error("从JWT令牌中获取用户ID失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT令牌中获取用户名
     *
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        if (!verifyToken(token)) {
            return null;
        }

        try {
            JWT jwt = parseToken(token);
            return jwt.getPayload().getClaimsJson().getStr("username");
        } catch (Exception e) {
            log.error("从JWT令牌中获取用户名失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT令牌中获取指定的载荷值
     *
     * @param token JWT令牌
     * @param key   键
     * @param <T>   值类型
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public <T> T getClaimFromToken(String token, String key) {
        if (!verifyToken(token)) {
            return null;
        }

        try {
            JWT jwt = parseToken(token);
            return (T) jwt.getPayload().getClaim(key);
        } catch (Exception e) {
            log.error("从JWT令牌中获取载荷值失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取JWT令牌的过期时间
     *
     * @param token JWT令牌
     * @return 过期时间
     */
    public DateTime getExpirationDateFromToken(String token) {
        if (!verifyToken(token)) {
            return null;
        }

        try {
            JWT jwt = parseToken(token);
            // 使用getDate方法直接获取Date类型的过期时间
            Date expDate = jwt.getPayload().getClaimsJson().getDate(JWTPayload.EXPIRES_AT);
            if (expDate != null) {
                return new DateTime(expDate);
            }
            return null;
        } catch (Exception e) {
            log.error("从JWT令牌中获取过期时间失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 判断JWT令牌是否过期
     *
     * @param token JWT令牌
     * @return 是否过期
     */
    public boolean isTokenExpired(String token) {
        DateTime expireDate = getExpirationDateFromToken(token);
        return expireDate == null || expireDate.isBefore(DateTime.now());
    }

    /**
     * 刷新JWT令牌
     *
     * @param token JWT令牌
     * @return 新的JWT令牌
     */
    public String refreshToken(String token) {
        if (!verifyToken(token)) {
            return null;
        }

        try {
            JWT jwt = parseToken(token);
            Long userId = jwt.getPayload().getClaimsJson().getLong("userId", -1L);
            String username = jwt.getPayload().getClaimsJson().getStr("username");

            // 创建一个新的payload，复制除了时间相关字段外的所有字段
            Map<String, Object> claims = new HashMap<>();
            jwt.getPayload().getClaimsJson().forEach((key, value) -> {
                if (!JWTPayload.ISSUED_AT.equals(key) &&
                        !JWTPayload.EXPIRES_AT.equals(key) &&
                        !JWTPayload.NOT_BEFORE.equals(key)) {
                    claims.put(key, value);
                }
            });

            // 生成新的JWT令牌
            return generateToken(userId, username, claims);
        } catch (Exception e) {
            log.error("刷新JWT令牌失败：{}", e.getMessage());
            return null;
        }
    }
}