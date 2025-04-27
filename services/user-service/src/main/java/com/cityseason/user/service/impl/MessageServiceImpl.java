package com.cityseason.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cityseason.common.config.RabbitConfig;
import com.cityseason.common.util.RequestContext;
import com.cityseason.user.domain.dto.MessageDTO;
import com.cityseason.user.domain.enums.MessageType;
import com.cityseason.user.domain.enums.UserRole;
import com.cityseason.user.domain.po.Message;
import com.cityseason.user.domain.po.MessageUser;
import com.cityseason.user.domain.po.User;
import com.cityseason.user.domain.vo.MessageUserVO;
import com.cityseason.user.domain.vo.MessageVO;
import com.cityseason.user.mapper.MessageMapper;
import com.cityseason.user.mapper.UserMapper;
import com.cityseason.user.service.IMessageService;
import com.cityseason.user.service.IMessageUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-25
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    private final UserMapper userMapper;

    private final IMessageUserService messageUserService;

    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO sendSystemMessage(MessageDTO messageDTO, Long[] userIds) {
        // 验证管理员身份
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        if (user == null || user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("无权限");
        }

        // 保存消息
        Message message = new Message();
        BeanUtils.copyProperties(messageDTO, message);
        message.setType(MessageType.SYSTEM);
        message.setUserId(user.getId());
        save(message);


        Collection<MessageUser> messageUsers = new ArrayList<>();

        for (Long userId : userIds) {
            MessageUser messageUser = new MessageUser();
            messageUser.setMessageId(message.getId());
            messageUser.setUserId(userId);
            messageUsers.add(messageUser);
        }
        try {
            messageUserService.saveBatch(messageUsers);
        } catch (Exception e) {
            throw new RuntimeException("消息发送失败，请检查接收者ID是否正确");
        }

        // 发送消息到RabbitMQ
        for (MessageUser messageUser : messageUsers) {
            MessageUserVO messageUserVO = new MessageUserVO(message, messageUser);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,
                    RabbitConfig.ROUTING_KEY,
                    messageUserVO
            );
        }

        return BeanUtil.copyProperties(message, MessageVO.class);
    }

    @Override
    public MessageVO sendCreatorMessage(MessageDTO messageDTO) {
        // 验证用户身份
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        if (user == null || user.getRole() != UserRole.CREATOR) {
            throw new RuntimeException("无权限");
        }
        // 保存消息
        Message message = new Message();
        BeanUtils.copyProperties(messageDTO, message);
        message.setType(MessageType.CREATOR);
        message.setUserId(user.getId());
        save(message);

        // TODO：查询所有订阅者，并发送消息


        return BeanUtil.copyProperties(message, MessageVO.class);
    }
}
