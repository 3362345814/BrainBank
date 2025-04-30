package com.cityseason.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cityseason.api.domin.enums.UserRole;
import com.cityseason.api.domin.vo.UserVO;
import com.cityseason.common.config.RabbitConfig;
import com.cityseason.common.util.RequestContext;
import com.cityseason.user.domain.dto.MessageDTO;
import com.cityseason.user.domain.enums.MessageType;
import com.cityseason.user.domain.po.Message;
import com.cityseason.user.domain.po.User;
import com.cityseason.user.domain.vo.MessageListVO;
import com.cityseason.user.domain.vo.MessageUserVO;
import com.cityseason.user.domain.vo.MessageVO;
import com.cityseason.user.mapper.MessageMapper;
import com.cityseason.user.mapper.UserMapper;
import com.cityseason.user.service.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO sendSystemMessage(MessageDTO messageDTO, Long[] userIds) {

        User user = userMapper.selectById(RequestContext.getCurrentUserId());

        // 保存消息
        MessageUserVO messageUserVO = new MessageUserVO();
        BeanUtils.copyProperties(messageDTO, messageUserVO);
        messageUserVO.setType(MessageType.of(messageDTO.getType()));

        if (messageUserVO.getType().equals(MessageType.SYSTEM) || messageUserVO.getType().equals(MessageType.PROMOTION)) {
            if (user.getRole() != UserRole.SUPER_ADMIN) {
                throw new RuntimeException("无权限");
            }
        } else if (messageUserVO.getType().equals(MessageType.CREATOR)) {
            throw new RuntimeException("无权限");
        }

        messageUserVO.setSenderId(user.getId());


        Collection<Message> messages = new ArrayList<>();

        for (Long userId : userIds) {
            Message messageUser = BeanUtil.copyProperties(messageUserVO, Message.class);

            messageUser.setReceiverId(userId);
            messages.add(messageUser);
        }
        try {
            saveBatch(messages);
        } catch (Exception e) {
            throw new RuntimeException("消息发送失败，请检查接收者ID是否正确");
        }

        // 发送消息到RabbitMQ
        for (Message message : messages) {
            MessageUserVO vo = BeanUtil.copyProperties(message, MessageUserVO.class);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,
                    RabbitConfig.ROUTING_KEY,
                    vo
            );
        }

        return BeanUtil.copyProperties(messageUserVO, MessageVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO sendCreatorMessage(MessageDTO messageDTO) {
        // 验证用户身份
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        if (user == null || user.getRole() != UserRole.CREATOR) {
            throw new RuntimeException("无权限");
        }
        // 保存消息
        MessageUserVO messageUserVO = new MessageUserVO();
        BeanUtils.copyProperties(messageDTO, messageUserVO);
        messageUserVO.setType(MessageType.CREATOR);
        messageUserVO.setSenderId(user.getId());

        // TODO：查询所有订阅者，并发送消息


        return BeanUtil.copyProperties(messageUserVO, MessageVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO sendSystemMessageToRole(MessageDTO messageDTO, Integer userRole) {
        // 验证管理员身份
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        if (user == null || user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("无权限");
        }
        MessageUserVO messageUserVO = new MessageUserVO();
        BeanUtils.copyProperties(messageDTO, messageUserVO);
        messageUserVO.setType(MessageType.of(messageDTO.getType()));
        messageUserVO.setSenderId(user.getId());


        // 查询所有符合条件的用户
        Collection<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getRole, userRole));

        // 保存消息
        Collection<Message> messages = new ArrayList<>();
        for (User u : users) {
            Message message = BeanUtil.copyProperties(messageUserVO, Message.class);

            message.setReceiverId(u.getId());
            messages.add(message);
        }

        try {
            saveBatch(messages);
        } catch (Exception e) {
            throw new RuntimeException("消息发送失败");
        }

        // 发送消息到RabbitMQ
        for (User u : users) {
            MessageUserVO vo = BeanUtil.copyProperties(messageUserVO, MessageUserVO.class);
            vo.setReceiverId(u.getId());
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,
                    RabbitConfig.ROUTING_KEY,
                    messageUserVO
            );
        }

        return BeanUtil.copyProperties(messageUserVO, MessageVO.class);
    }

    @Override
    public MessageListVO getUserMessageList(Long userId) {
        // 验证用户身份
        User user = userMapper.selectById(RequestContext.getCurrentUserId());
        if (user == null || !user.getId().equals(userId)) {
            throw new RuntimeException("无权限");
        }

        MessageListVO messageListVO = new MessageListVO();

        // 判断用户角色
        if (user.getRole() == UserRole.SUPER_ADMIN) {
            // 查看发出过的系统消息
            Set<MessageVO> sentSystemMessages = new HashSet<>(BeanUtil.copyToList(
                    list(new LambdaQueryWrapper<Message>()
                            .eq(Message::getSenderId, userId)
                            .eq(Message::getType, MessageType.SYSTEM)
                            .orderByDesc(Message::getCreatedAt)),

                    MessageVO.class
            ));
            messageListVO.setSentSystemMessages(sentSystemMessages);

            // 查看发出过的活动消息
            Set<MessageVO> sentPromotionMessages = new HashSet<>(BeanUtil.copyToList(
                    list(new LambdaQueryWrapper<Message>()
                            .eq(Message::getSenderId, userId)
                            .eq(Message::getType, MessageType.PROMOTION)
                            .orderByDesc(Message::getCreatedAt)),
                    MessageVO.class
            ));
            messageListVO.setSentPromotionMessages(sentPromotionMessages);
        }
        // 查看收到的系统消息
        List<MessageUserVO> receivedSystemMessages = BeanUtil.copyToList(
                list(new LambdaQueryWrapper<Message>()
                        .eq(Message::getReceiverId, userId)
                        .eq(Message::getType, MessageType.SYSTEM)
                        .orderByDesc(Message::getCreatedAt)),
                MessageUserVO.class
        );
        messageListVO.setReceivedSystemMessages(receivedSystemMessages);

        // 查看收到的活动消息
        List<MessageUserVO> receivedPromotionMessages = BeanUtil.copyToList(
                list(new LambdaQueryWrapper<Message>()
                        .eq(Message::getReceiverId, userId)
                        .eq(Message::getType, MessageType.PROMOTION)
                        .orderByDesc(Message::getCreatedAt)),
                MessageUserVO.class
        );
        messageListVO.setReceivedPromotionMessages(receivedPromotionMessages);

        // 查看收到的用户消息
        // 查询所有收到的用户消息
        List<MessageUserVO> receivedUserMessages = BeanUtil.copyToList(
                list(new LambdaQueryWrapper<Message>()
                        .eq(Message::getReceiverId, userId)
                        .in(Message::getType, MessageType.PRIVATE, MessageType.CREATOR)),
                MessageUserVO.class
        );
        // 查询所有发送的用户消息
        List<MessageUserVO> sentUserMessages = BeanUtil.copyToList(
                list(new LambdaQueryWrapper<Message>()
                        .eq(Message::getSenderId, userId)
                        .in(Message::getType, MessageType.PRIVATE, MessageType.CREATOR)),
                MessageUserVO.class
        );
        // 找出两个列表中的所有用户ID
        Set<Long> userIds = new HashSet<>();
        for (MessageUserVO message : receivedUserMessages) {
            userIds.add(message.getSenderId());
        }
        for (MessageUserVO message : sentUserMessages) {
            userIds.add(message.getReceiverId());
        }

        // 查询所有用户信息
        List<UserVO> users = BeanUtil.copyToList(userMapper.selectList(new LambdaQueryWrapper<User>().in(User::getId, userIds)), UserVO.class);

        // 遍历所有消息，找出每个用户的消息
        // 先把用户列表转成 Map<用户ID, UserVO>，方便快速查找
        Map<Long, UserVO> userMap = users.stream()
                .collect(Collectors.toMap(UserVO::getId, userVO -> userVO));

        // 初始化结果
        Map<UserVO, List<MessageUserVO>> result = new HashMap<>();

        // 处理发出的消息（你 -> 其他用户）
        for (MessageUserVO message : sentUserMessages) {
            UserVO userVO = userMap.get(message.getReceiverId());
            if (userVO != null) {
                // 检查是否已经存在该用户的条目
                result.computeIfAbsent(userVO, k -> new ArrayList<>()).add(message);
            }
        }

        // 处理收到的消息（其他用户 -> 你）
        for (MessageUserVO message : receivedUserMessages) {
            UserVO userVO = userMap.get(message.getSenderId());
            if (userVO != null) {
                // 检查是否已经存在该用户的条目
                result.computeIfAbsent(userVO, k -> new ArrayList<>()).add(message);
            }
        }

        // 构建 UserAndMessage 列表
        List<MessageListVO.UserAndMessage> userAndMessagesList = new ArrayList<>();
        for (Map.Entry<UserVO, List<MessageUserVO>> entry : result.entrySet()) {
            MessageListVO.UserAndMessage userAndMessage = new MessageListVO.UserAndMessage();
            userAndMessage.setUser(entry.getKey());

            // 排序消息：按照发送时间进行升序排序
            List<MessageUserVO> sortedMessages = entry.getValue().stream()
                    .sorted(Comparator.comparing(MessageUserVO::getCreatedAt))
                    .collect(Collectors.toList());

            userAndMessage.setMessage(sortedMessages);
            userAndMessagesList.add(userAndMessage);
        }

        // 最终设置到 MessageListVO 对象中
        messageListVO.setUserMessages(userAndMessagesList);

        return messageListVO;
    }
}
