package com.cityseason.content.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.content.domain.dto.ContentTagDTO;
import com.cityseason.content.domain.po.ContentTag;
import com.cityseason.content.domain.po.Tag;
import com.cityseason.content.domain.query.ContentTagQuery;
import com.cityseason.content.domain.vo.ContentTagVO;
import com.cityseason.content.domain.vo.TagVO;
import com.cityseason.content.mapper.ContentTagMapper;
import com.cityseason.content.service.IContentTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author author
 * @since 2025-04-26
 */
@Service
public class ContentTagServiceImpl extends ServiceImpl<ContentTagMapper, ContentTag> implements IContentTagService {

    @Override
    public ContentTagVO addTag(ContentTagDTO contentTagDTO) {
        //查询是否再标签表里面有这个标签
        Long tagId = contentTagDTO.getTagId();
        ContentTag contentTag = new ContentTag();
        BeanUtils.copyProperties(contentTagDTO, contentTag);
        // 判断 tagId 是否为 null
        if (tagId != null) {
            // 使用 Db 静态工具查询 Tag 表
            Tag tag = Db.lambdaQuery(Tag.class)
                    .eq(Tag::getId, tagId)
                    .one();
            if (tag != null) {
                this.save(contentTag);
                ContentTagVO contentTagVO = new ContentTagVO();
                BeanUtils.copyProperties(contentTag, contentTagVO);
                return contentTagVO;
            }else {
                throw new RuntimeException("标签不存在");
            }
        }else {
            throw new RuntimeException("标签Id不能为空");
        }
    }

    @Override
    public ContentTagVO updateTag(ContentTagDTO contentTagDTO) {
        Long tagId = contentTagDTO.getTagId();
        ContentTag contentTag = new ContentTag();
        BeanUtils.copyProperties(contentTagDTO, contentTag);
        if (tagId != null) {
            // 使用 Db 静态工具查询 Tag 表
            Tag tag = Db.lambdaQuery(Tag.class)
                    .eq(Tag::getId, tagId)
                    .one();
            if (tag != null) {
                this.updateById(contentTag);
                ContentTagVO contentTagVO = new ContentTagVO();
                BeanUtils.copyProperties(contentTag, contentTagVO);
                return contentTagVO;
            }else {
                throw new RuntimeException("标签不存在");
            }
        }else {
            throw new RuntimeException("标签Id不能为空");
        }

    }

    @Override
    public PageDTO<ContentTagVO> queryByPage(ContentTagQuery contentTagQuery) {
        return null;
    }
}
