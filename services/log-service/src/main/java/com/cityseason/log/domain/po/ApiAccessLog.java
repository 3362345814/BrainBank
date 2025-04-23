package com.cityseason.log.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 接口访问日志表
 * </p>
 *
 * @author 林心海
 * @since 2025-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_access_log")
public class ApiAccessLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求方式（GET/POST等）
     */
    private String requestMethod;

    /**
     * 调用方法
     */
    private String classMethod;

    /**
     * 客户端IP
     */
    private String ipAddress;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应内容
     */
    private String responseData;

    /**
     * 响应时间(ms)
     */
    private Integer responseTime;

    /**
     * HTTP响应状态码
     */
    private Integer statusCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
