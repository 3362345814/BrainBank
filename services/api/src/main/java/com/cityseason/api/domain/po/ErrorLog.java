package com.cityseason.api.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统异常日志表
 * </p>
 *
 * @author 林心海
 * @since 2025-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ErrorLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 堆栈信息
     */
    private String stackTrace;

    /**
     * 发生时间
     */
    private LocalDateTime occurredAt;

    /**
     * 模块名称
     */
    private String module;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;


    /**
     * 线程名
     */
    private String threadName;


}
