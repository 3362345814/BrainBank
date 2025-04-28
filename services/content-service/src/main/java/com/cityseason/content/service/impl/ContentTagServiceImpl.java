package com.cityseason.content.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.content.domain.dto.ContentTagDTO;
import com.cityseason.content.domain.po.ContentTag;
import com.cityseason.content.domain.po.Tag;
import com.cityseason.content.domain.query.ContentTagQuery;
import com.cityseason.content.domain.vo.ContentTagVO;

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

    private final ContentTagMapper contentTagMapper;

    public ContentTagServiceImpl(ContentTagMapper contentTagMapper) {
        this.contentTagMapper = contentTagMapper;
    }

    @Override
    public ContentTagVO addTag(ContentTagDTO contentTagDTO) {
        //查询是否再标签表里面有这个标签
        Long tagId = contentTagDTO.getTagId();
        ContentTag contentTag = new ContentTag();
        BeanUtils.copyProperties(contentTagDTO, contentTag);
        // 判断 tagId 是否为 null
        if (tagId != null) {

            //判断是否存在contentTag里面
            ContentTag contentTag1 = lambdaQuery()
                    .eq(ContentTag::getContentId, contentTagDTO.getContentId())
                    .eq(ContentTag::getTagId, tagId)
                    .one();
            if(contentTag1 != null){
                throw new RuntimeException("该内容的该标签已经存在");
            }
            // 使用 Db 静态工具查询 Tag 表,并判断是否存在
            Tag tag = Db.lambdaQuery(Tag.class)
                    .eq(Tag::getId, tagId)
                    .one();
            if (tag != null) {
                this.save(contentTag);

                ContentTagVO contentTagVO = new ContentTagVO();
                BeanUtil.copyProperties(contentTag, contentTagVO);

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
        Tag tag = new Tag();

        //如果TagId为空，就查询TagName，并获取TagId
        if(contentTagQuery.getTagId() == null&&contentTagQuery.getTagName() != null) {
            tag = Db.lambdaQuery(Tag.class)
                    .eq(Tag::getName, contentTagQuery.getTagName())
                    .one();
            contentTagQuery.setTagId(tag.getId());
            if (tag == null) {
                throw new RuntimeException("标签不存在");
            }
        }
        //如果TagId不为空，就优先查询TagId（不用查Tag表了）
        //contentTagQuery.setTagId(tag.getId());
            //赋值排序字段,如果为空就默认排序
            //如果排序字段为空，就默认排序
            Page<ContentTag> page;
            if (contentTagQuery.getSortBy() == null && contentTagQuery.getIsAsc() == null) {
                page = contentTagQuery.toMpPage("id", true);
            }else if (contentTagQuery.getSortBy() == null || contentTagQuery.getIsAsc() != null) {
                page = contentTagQuery.toMpPage("id", contentTagQuery.getIsAsc());
            }
            else if (contentTagQuery.getSortBy() != null && contentTagQuery.getIsAsc() == null) {
                page = contentTagQuery.toMpPage(contentTagQuery.getSortBy(), true);
            }
            else {
                page = contentTagQuery.toMpPage(contentTagQuery.getSortBy(), contentTagQuery.getIsAsc());
            }
            LambdaQueryWrapper<ContentTag> wrapper = new LambdaQueryWrapper<ContentTag>()
                    .eq(contentTagQuery.getContentId()!= null, ContentTag::getContentId, contentTagQuery.getContentId())
                    .eq(contentTagQuery.getTagId()!= null, ContentTag::getTagId, contentTagQuery.getTagId());
            page(page, wrapper);
            return PageDTO.of(page, ContentTagVO.class);


    }
}
