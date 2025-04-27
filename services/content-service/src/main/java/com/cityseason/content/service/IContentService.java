package com.cityseason.content.service;

import com.cityseason.common.domain.dto.PageDTO;
import com.cityseason.content.domain.dto.ContentDTO;
import com.cityseason.content.domain.po.Content;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.content.domain.query.ContentQuery;
import com.cityseason.content.domain.vo.ContentVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-04-26
 */
public interface IContentService extends IService<Content> {

    ContentVO saveContent(ContentDTO content);

    ContentVO updateContent(ContentDTO contentDTO);

    PageDTO<ContentVO> queryContentPage(ContentQuery contentQuery);

    ContentVO queryById(Long id);
}
