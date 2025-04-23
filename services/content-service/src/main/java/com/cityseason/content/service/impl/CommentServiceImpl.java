package com.cityseason.content.service.impl;

import com.cityseason.content.domain.po.Comment;
import com.cityseason.content.mapper.CommentMapper;
import com.cityseason.content.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-23
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
