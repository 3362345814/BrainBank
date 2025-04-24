package com.cityseason.content.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ContentDTO {

    /**
     * 作者ID
     */
    @NotNull(message = "作者ID不能为空")
    private Long authorId;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String title;

    /**
     * 内容简介
     */
    @NotNull(message = "内容简介不能为空")
    private String description;

    /**
     * 介绍页图片
     */
    @NotNull(message = "介绍页图片不能为空")
    private MultipartFile imageMultipartFile;

    /**
     * 文件
     */
    @NotNull(message = "文件不能为空")
    private MultipartFile contentMultipartFile;

    /**
     * 内容类型
     */
    @NotNull(message = "内容类型不能为空")
    private int contentType;

    /**
     * 购买价格
     */
    private BigDecimal price;
    private String saleMode;
    private String publishStatus;
    private String tags;
}
