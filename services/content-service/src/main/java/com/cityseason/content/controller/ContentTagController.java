package com.cityseason.content.controller;


import com.cityseason.api.domain.vo.Result;
import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.content.domain.dto.ContentTagDTO;
import com.cityseason.content.domain.query.ContentTagQuery;
import com.cityseason.content.domain.vo.ContentTagVO;
import com.cityseason.content.service.impl.ContentTagServiceImpl;
import com.cityseason.content.service.impl.TagServiceImpl;
import com.cityseason.log.annotation.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内容标签管理
 *
 * @author author
 * @since 2025-04-26
 */
@RestController
@RequestMapping("/content/content-tag")
@RequiredArgsConstructor
public class ContentTagController {
    private final TagServiceImpl tagService;
    private final ContentTagServiceImpl contentTagService;

    /**
     * 添加标签
     *
     * @param ContentTagDTO 标签DTO
     * @return 标签VO
     */
    @PostMapping
    @OperationLog(module = "内容标签管理", operation = "为内容添加标签")
    public Result<ContentTagVO> addTag(@Valid @RequestBody ContentTagDTO ContentTagDTO) {
        try {
            ContentTagVO ContentTagVO = contentTagService.addTag(ContentTagDTO);
            return Result.success(ContentTagVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 删除标签
     *
     * @param id 标签id
     * @return 标签信息
     */
    @DeleteMapping("/delete/{id}")
    @OperationLog(module = "内容标签管理", operation = "删除内容标签")
    public Result deleteTag(@PathVariable Long id) {
        try {
            contentTagService.removeById(id);
            return Result.success(true);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 修改内容标签
     *
     * @param ContentTagDTO 标签信息
     * @return 标签信息
     */
    @PutMapping
    @OperationLog(module = "内容标签管理", operation = "修改内容标签")
    public Result<ContentTagVO> updateTag(@Valid @RequestBody ContentTagDTO ContentTagDTO) {
        try {
            ContentTagVO ContentTagVO = contentTagService.updateTag(ContentTagDTO);
            return Result.success(ContentTagVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 分页获取标签列表
     *
     * @param ContentTagQuery 标签查询条件
     * @return 标签列表
     */
    @GetMapping("/page")
    @OperationLog(module = "内容标签管理", operation = "分页获取内容标签列表")
    public Result<PageDTO<ContentTagVO>> page(@Valid ContentTagQuery ContentTagQuery) {
        try {
            PageDTO<ContentTagVO> page = contentTagService.queryByPage(ContentTagQuery);
            return Result.success(page);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 根据内容id获取标签列表
     *
     * @param contentId 内容id
     * @return 标签列表
     */
    @GetMapping("/select-by-content")
    @OperationLog(module = "内容标签管理", operation = "根据内容id获取标签列表")
    public Result<List<ContentTagVO>> selectByContentId(@RequestParam Long contentId) {
        try {
            List<ContentTagVO> ContentTagVO = contentTagService.selectByContentId(contentId);
            return Result.success(ContentTagVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }


}
