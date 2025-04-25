package com.cityseason.user.service.impl;

import com.cityseason.user.domain.po.Message;
import com.cityseason.user.mapper.MessageMapper;
import com.cityseason.user.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-25
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
