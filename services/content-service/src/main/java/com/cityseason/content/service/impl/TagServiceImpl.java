package com.cityseason.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cityseason.content.domain.dto.TagDTO;
import com.cityseason.content.domain.po.Tag;
import com.cityseason.content.domain.vo.TagVO;
import com.cityseason.content.mapper.TagMapper;
import com.cityseason.content.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2025-04-26
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Override
    public TagVO addTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        String tagName = tagDTO.getName();
        if (tagName != null) {
            // 使用链式调用和条件判断简化代码
            if (this.getOne(new QueryWrapper<Tag>().eq("name", tagName)) != null) {
                throw new RuntimeException("标签名已存在，不能重复添加");
            }
        }
        // 保存新标签到数据库
        BeanUtils.copyProperties(tagDTO, tag);
        this.save(tag);

        TagVO tagVO = new TagVO();
        BeanUtils.copyProperties(tag, tagVO);
        return tagVO;
    }
}
