package com.cityseason.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解，标记需要记录操作日志的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    /**
     * 操作模块
     */
    String module() default "";

    /**
     * 操作描述
     */
    String operation() default "";
}