package com.cityseason.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.content.domain.dto.ContentTagDTO;
import com.cityseason.content.domain.po.ContentTag;
import com.cityseason.content.domain.query.ContentTagQuery;
import com.cityseason.content.domain.vo.ContentTagVO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author author
 * @since 2025-04-26
 */
public interface IContentTagService extends IService<ContentTag> {

    ContentTagVO addTag(@Valid ContentTagDTO contentTagDTO);

    ContentTagVO updateTag(@Valid ContentTagDTO contentTagDTO);

    PageDTO<ContentTagVO> queryByPage(@Valid ContentTagQuery contentTagQuery);

    List<ContentTagVO> selectByContentId(Long contentId);
}
