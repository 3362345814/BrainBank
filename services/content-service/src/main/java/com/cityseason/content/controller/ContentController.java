package com.cityseason.content.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品内容管理
 *
 * @author 林心海
 * @since 2025-04-23
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    /**
     * 添加商品
     *
     * @param content 商品内容
     * @return 商品ID
     */
    @RequestMapping("/add")
    public Long addContent(String content) {
        // 模拟添加商品逻辑
        return 1L;
    }

}
