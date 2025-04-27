package com.cityseason.content.controller;


import com.cityseason.common.domain.vo.Result;
import com.cityseason.content.domain.dto.TagDTO;
import com.cityseason.content.domain.po.Tag;
import com.cityseason.content.domain.vo.TagVO;
import com.cityseason.content.service.impl.TagServiceImpl;
import com.cityseason.log.annotation.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 标签管理
 *
 * @author author
 * @since 2025-04-26
 */
@RestController
@RequestMapping("/content/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagServiceImpl tagService;
    /**
     * 新增标签
     *
     * @param tagDTO 标签信息
     * @return 标签信息
     */
    @GetMapping("/add")
    @OperationLog(module = "标签管理", operation = "新增标签")
    public Result<TagVO> addTag(@Valid @RequestBody TagDTO tagDTO) {
        try {
            TagVO tagVO = tagService.addTag(tagDTO);
            return Result.success(tagVO);
        }
        catch (Exception e) {
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
    @OperationLog(module = "标签管理", operation = "删除标签")
    public Result<Boolean> deleteTag(@PathVariable Long id) {
        try {
            tagService.removeById(id);
            return Result.success(true);
        }
        catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }
    /**
     * 修改标签
     *
     * @param tagDTO 标签信息
     * @return 标签信息
     */
    @PutMapping("/update")
    @OperationLog(module = "标签管理", operation = "修改标签")
    public Result<Boolean> updateTag(@Valid @RequestBody TagDTO tagDTO) {
        try {
            if (tagDTO.getId() == null) {
                return Result.failure(400, "标签id不能为空");
            }
            Tag tag = new Tag();
            BeanUtils.copyProperties(tagDTO, tag);
            tagService.updateById(tag);
            return Result.success(true);
        }
        catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }
    /**
     * 查询标签
     *
     * @param id 标签id
     * @return 标签信息
     */
    @GetMapping("/get/{id}")
    @OperationLog(module = "标签管理", operation = "查询标签")
    public Result<TagVO> getTag(@PathVariable Long id) {
        try {
            Tag tag = tagService.getById(id);
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            return Result.success(tagVO);
        }
        catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

}
