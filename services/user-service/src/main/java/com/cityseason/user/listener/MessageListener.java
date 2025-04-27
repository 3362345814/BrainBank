package com.cityseason.user.listener;

import com.alibaba.fastjson.JSON;
import com.cityseason.common.websocket.WebSocketHandler;
import com.cityseason.user.domain.vo.MessageUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MessageListener {


    @RabbitListener(queues = "message-queue")
    public void handleMessage(MessageUserVO messageUserVO) {
        try {
            WebSocketHandler.sendToUser(messageUserVO.getReceiverId(), JSON.toJSONString(messageUserVO));
        } catch (Exception e) {
            throw new RuntimeException("消息发送失败");
        }
    }
}
