package com.cityseason.user.controller;


import com.cityseason.common.domain.vo.Result;
import com.cityseason.user.domain.dto.MessageDTO;
import com.cityseason.user.domain.vo.MessageVO;
import com.cityseason.user.service.IMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息管理
 *
 * @author 林心海
 * @since 2025-04-25
 */
@RestController
@RequestMapping("/user/message")
@RequiredArgsConstructor
public class MessageController {

    public final IMessageService messageService;

    /**
     * 发送系统消息
     *
     * @param messageDTO 消息实体
     * @param userIds    接收者ID列表
     * @return 消息实体
     */
    @PostMapping("/send-system-message")
    public Result<MessageVO> sendSystemMessage(@Valid @RequestBody MessageDTO messageDTO, Long[] userIds) {
        try {
            MessageVO messageVO = messageService.sendSystemMessage(messageDTO, userIds);
            return Result.success(messageVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 发送创作者消息
     *
     * @param messageDTO 消息实体
     * @return 消息实体
     */
    @PostMapping("/send-creator-message")
    public Result<MessageVO> sendCreatorMessage(@Valid @RequestBody MessageDTO messageDTO) {
        try {
            MessageVO messageVO = messageService.sendCreatorMessage(messageDTO);
            return Result.success(messageVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

}
