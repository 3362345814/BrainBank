package com.cityseason.api.domin.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author 林心海
 * @since 2025-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OperationLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作描述
     */
    private String operation;

    /**
     * 调用方法
     */
    private String method;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求方式（GET/POST等）
     */
    private String requestType;

    /**
     * 请求IP
     */
    private String ipAddress;

    /**
     * User-Agent信息
     */
    private String userAgent;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 操作状态（1正常，0异常）
     */
    private Integer status;

    /**
     * 错误信息（如有）
     */
    private String errorMsg;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
