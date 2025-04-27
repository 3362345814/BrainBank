package com.cityseason.content.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.common.util.RequestContext;
import com.cityseason.content.domain.dto.ContentDTO;
import com.cityseason.content.domain.dto.ImageDTO;
import com.cityseason.content.domain.po.Content;
import com.cityseason.content.domain.po.ContentImage;
import com.cityseason.content.domain.query.ContentQuery;
import com.cityseason.content.domain.vo.ContentVO;
import com.cityseason.content.mapper.ContentMapper;
import com.cityseason.content.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.undertow.server.RequestStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * Content 服务实现类
 *
 * @author author
 * @since 2025-04-26
 */
@Service
@RequiredArgsConstructor
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService {

    private final ContentImageServiceImpl contentImageServiceImpl;

    /**
     * 添加内容以及内容图片表
     * @param contentDTO
     * @return
     */
    @Override
    public ContentVO saveContent(ContentDTO contentDTO) {
        Content content = new Content();
        BeanUtils.copyProperties(contentDTO, content);
        if(!RequestContext.getCurrentUserId().equals(content.getUserId())) {
            throw new RuntimeException("用户ID不匹配");
        }
        if (save(content)) {
            //保存内容表
            Long contentId = content.getId();
            //保存内容图像表
            if (contentDTO.getImages() != null && contentDTO.getImages().size() > 0) {
                Collection<ContentImage> images = new ArrayList<>();
                for (ImageDTO image : contentDTO.getImages()) {
                    ContentImage Image = new ContentImage();
                    BeanUtils.copyProperties(image, Image);
                    Image.setContentId(contentId);
                    images.add(Image);
                }
                if (! contentImageServiceImpl.saveBatch(images)) {
                    throw new RuntimeException("保存内容图像表失败");
                }
                ContentVO contentVO = new ContentVO();
                BeanUtils.copyProperties(content, contentVO);
                return contentVO;
            }
        }
        return null;
    }

    /**
     *
     * 修改内容以及内容图片表
     *
     *
     * @param contentDTO
     * @return
     */
    @Override
    public ContentVO updateContent(ContentDTO contentDTO) {
        Content content = new Content();
        BeanUtils.copyProperties(contentDTO, content);
        if(!RequestContext.getCurrentUserId().equals(content.getUserId())) {
            throw new RuntimeException("用户ID不匹配");
        }
        if (updateById(content)) {
            //修改内容表
            Long contentId = content.getId();
            if (contentDTO.getImages() != null && contentDTO.getImages().size() > 0) {
                Collection<ContentImage> images = new ArrayList<>();
                for (ImageDTO image : contentDTO.getImages()) {
                    ContentImage Image = new ContentImage();
                    BeanUtils.copyProperties(image, Image);
                    if(image.getContentId().equals(contentId)) {
                        images.add(Image);
                    }else {
                        throw new RuntimeException("内容ID不匹配");
                    }
                }
                if (! contentImageServiceImpl.saveBatch(images)) {
                    throw new RuntimeException("保存内容图像表失败");
                }
                ContentVO contentVO = new ContentVO();
                BeanUtils.copyProperties(content, contentVO);
                return contentVO;
            }
        }
        System.out.println("修改失败");
        return null;
    }

    @Override
    public PageDTO<ContentVO> queryContentPage(ContentQuery contentQuery) {
        return null;
    }

    @Override
    public ContentVO queryById(Long id) {
        Content content = null;
        content = getById(id);
        ContentVO contentVO = new ContentVO();
        try {
            BeanUtils.copyProperties(content, contentVO);
        } catch (Exception e) {
            throw new RuntimeException("ID查询内容失败");
        }
        if(contentVO!= null) {
            return contentVO;
        }
        return null;
    }


}
