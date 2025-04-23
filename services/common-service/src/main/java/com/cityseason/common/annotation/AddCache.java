package com.cityseason.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AddCache {
    /**
     * 缓存前缀
     */
    String prefix() default "";

    /**
     * 过期时间，单位秒，默认1小时
     */
    long expire() default 3600L;
}