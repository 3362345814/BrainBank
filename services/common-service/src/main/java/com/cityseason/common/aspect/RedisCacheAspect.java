package com.cityseason.common.aspect;

import com.cityseason.common.annotation.AddCache;
import com.cityseason.common.annotation.DelCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisCacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.cityseason.common.annotation.AddCache)")
    public void addCachePointcut() {
    }

    @Pointcut("@annotation(com.cityseason.common.annotation.DelCache)")
    public void delCachePointcut() {
    }

    @Around("addCachePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 获取注解
        AddCache addCache = method.getAnnotation(AddCache.class);

        // 缓存前缀
        String prefix = addCache.prefix();
        // 过期时间（秒）
        long expire = addCache.expire();

        // 生成缓存key
        String key = prefix + "::" + Arrays.toString(point.getArgs());

        try {
            // 查询缓存
            Object cacheValue = redisTemplate.opsForValue().get(key);

            // 如果缓存中有数据，直接返回
            if (cacheValue != null) {
                log.info("从缓存中获取数据: {}", key);
                return cacheValue;
            }
        } catch (Exception e) {
            log.error("Redis缓存读取异常: {}", e.getMessage());
            // 发生异常时不影响正常业务，继续执行方法
        }

        // 执行方法
        log.info("从数据库中获取数据");
        Object result = point.proceed();

        // 如果结果不为空，则存入缓存
        if (result != null) {
            try {
                if (expire > 0) {
                    redisTemplate.opsForValue().set(key, result, expire, java.util.concurrent.TimeUnit.SECONDS);
                } else {
                    redisTemplate.opsForValue().set(key, result);
                }
                log.info("数据存入缓存: {}", key);
            } catch (Exception e) {
                log.error("Redis缓存写入异常: {}", e.getMessage());
                // 缓存写入异常不影响返回结果
            }
        }

        return result;
    }

    @AfterReturning("delCachePointcut()")
    public void afterReturning(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 获取注解
        DelCache delCache = method.getAnnotation(DelCache.class);

        // 缓存前缀
        String prefix = delCache.prefix();

        // 获取参数
        int keyIndex = delCache.keyIndex();
        Object[] args = point.getArgs();

        if (args.length > keyIndex) {
            // 两种清除缓存的方式：

            // 1. 如果知道确切的键，直接删除指定键
            String exactKey = prefix + "::" + "[" + args[keyIndex] + "]";
            redisTemplate.delete(exactKey);
            log.info("删除缓存: {}", exactKey);

            // 2. 或者使用模式匹配，删除所有相关缓存（更安全但性能较低）
            String pattern = prefix + "::*";
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("删除缓存前缀 {} 下的所有数据, 共 {} 条", prefix, keys.size());
            }
        }
    }


}