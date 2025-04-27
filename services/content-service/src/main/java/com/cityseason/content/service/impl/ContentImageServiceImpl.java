package com.cityseason.content.service.impl;

import com.cityseason.content.domain.dto.ImageDTO;
import com.cityseason.content.domain.po.ContentImage;
import com.cityseason.content.mapper.ContentImageMapper;
import com.cityseason.content.service.IContentImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ContentImageServiceImpl extends ServiceImpl<ContentImageMapper, ContentImage> implements IContentImageService {
    private final ContentImageMapper contentImageMapper;

    public void saveImage(Long contentId, ImageDTO imageDTO) {
        ContentImage contentImage = new ContentImage();
        contentImage.setContentId(contentId);
        //ImageDTO 有 getUrl 方法获取图片路径
        contentImage.setImageUrl(imageDTO.getImageUrl());
        contentImageMapper.insert(contentImage);
    }
}
