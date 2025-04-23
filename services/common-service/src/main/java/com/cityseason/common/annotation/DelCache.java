package com.cityseason.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DelCache {
    /**
     * 要清除的缓存前缀
     */
    String prefix();

    /**
     * 缓存键参数索引，默认为0（方法的第一个参数）
     */
    int keyIndex() default 0;

}
