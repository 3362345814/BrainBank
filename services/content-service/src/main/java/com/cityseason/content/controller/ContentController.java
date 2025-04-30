package com.cityseason.content.controller;


import com.cityseason.api.domin.vo.Result;
import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.content.domain.dto.ContentDTO;
import com.cityseason.content.domain.po.Content;
import com.cityseason.content.domain.query.ContentQuery;
import com.cityseason.content.domain.vo.ContentVO;
import com.cityseason.content.service.impl.ContentServiceImpl;
import com.cityseason.log.annotation.OperationLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 内容服务
 *
 * @author author
 * @since 2025-04-26
 */
@RestController
@RequestMapping("/content")
@Slf4j
@RequiredArgsConstructor
public class ContentController {
    private final ContentServiceImpl contentService;

    /**
     * 添加内容
     *
     * @param contentDTO
     * @return
     */
    @PostMapping("/add")
    @OperationLog(module = "内容服务", operation = "添加内容")
    public Result<ContentVO> add(@RequestBody ContentDTO contentDTO) {
        Content content = new Content();
        BeanUtils.copyProperties(contentDTO, content);
        ContentVO contentVO = null;
        try {
            contentVO = contentService.saveContent(contentDTO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
        if (contentVO != null) {
            return Result.success(contentVO);
        }
        return Result.failure(500, "添加内容失败");
    }

    /**
     * 删除内容
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @OperationLog(module = "内容服务", operation = "删除内容")
    public Result delete(@PathVariable Long id) {
        try {
            contentService.removeById(id);
            return Result.success("success");
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 修改内容
     *
     * @param contentDTO
     * @return
     */
    @PutMapping("/update")
    @OperationLog(module = "内容服务", operation = "修改内容")
    public Result<ContentVO> update(@RequestBody ContentDTO contentDTO) {
        Content content = new Content();
        BeanUtils.copyProperties(contentDTO, content);
        ContentVO contentVO = null;
        try {
            contentVO = contentService.updateContent(contentDTO);
            return Result.success(contentVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 分页查询内容
     *
     * @param ContentQuery
     * @return
     */
    @GetMapping("/page")
    @OperationLog(module = "内容服务", operation = "分页查询内容")
    public Result<PageDTO<ContentVO>> queryPage(ContentQuery ContentQuery) {
        try {
            PageDTO<ContentVO> pageDTO = contentService.queryContentPage(ContentQuery);
            return Result.success(pageDTO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 根据id查询内容
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @OperationLog(module = "内容服务", operation = "根据id查询内容")
    public Result<ContentVO> queryById(@PathVariable Long id) {
        try {
            ContentVO contentVO = contentService.queryById(id);
            return Result.success(contentVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }
}
