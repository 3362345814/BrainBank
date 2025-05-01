package com.cityseason.user.domain.vo;

import com.cityseason.api.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 消息列表VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageListVO {

    /**
     * 收到的系统消息列表
     */
    private Collection<MessageUserVO> receivedSystemMessages;

    /**
     * 收到的活动消息列表
     */
    private Collection<MessageUserVO> receivedPromotionMessages;

    /**
     * 发送的系统消息列表
     */
    private Collection<MessageVO> sentSystemMessages;

    /**
     * 发送的活动消息列表
     */
    private Collection<MessageVO> sentPromotionMessages;

    /**
     * 用户消息列表
     */
    private Collection<UserAndMessage> userMessages;


    @Data
    public static class UserAndMessage {
        private UserVO user;
        private Collection<MessageUserVO> message;
    }
}
