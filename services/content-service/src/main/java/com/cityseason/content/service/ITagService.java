package com.cityseason.content.service;

import com.cityseason.content.domain.dto.TagDTO;
import com.cityseason.content.domain.po.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.content.domain.vo.TagVO;
import jakarta.validation.Valid;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-04-26
 */
public interface ITagService extends IService<Tag> {

    TagVO addTag(@Valid TagDTO tagDTO);
}
