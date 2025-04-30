package com.cityseason.user.controller;


import com.cityseason.api.domin.vo.Result;
import com.cityseason.log.annotation.OperationLog;
import com.cityseason.user.domain.dto.MessageDTO;
import com.cityseason.user.domain.vo.MessageListVO;
import com.cityseason.user.domain.vo.MessageVO;
import com.cityseason.user.service.IMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
     * 发送系统消息、活动消息、私信到对应的用户
     *
     * @param messageDTO 消息实体
     * @param userIds    接收者ID列表
     * @return 消息实体
     */
    @PostMapping("/send-system-message")
    @OperationLog(module = "消息管理", operation = "发送系统消息、活动消息、私信到对应的用户")
    public Result<MessageVO> sendSystemMessage(@Valid @RequestBody MessageDTO messageDTO, @RequestParam Long[] userIds) {
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
    @OperationLog(module = "消息管理", operation = "发送创作者消息")
    public Result<MessageVO> sendCreatorMessage(@Valid @RequestBody MessageDTO messageDTO) {
        try {
            MessageVO messageVO = messageService.sendCreatorMessage(messageDTO);
            return Result.success(messageVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }


    /**
     * 发送系统消息或活动消息到某一类型用户
     *
     * @param messageDTO 消息实体
     * @param userRole   用户角色
     * @return 消息实体
     */
    @PostMapping("/send-system-message-to-role")
    @OperationLog(module = "消息管理", operation = "发送系统消息或活动消息到某一类型用户")
    public Result<MessageVO> sendSystemMessageToRole(@Valid @RequestBody MessageDTO messageDTO, @RequestParam Integer userRole) {
        try {
            MessageVO messageVO = messageService.sendSystemMessageToRole(messageDTO, userRole);
            return Result.success(messageVO);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }
    }

    /**
     * 获取用户的消息列表
     *
     * @param userId 用户ID
     * @return 消息列表
     */
    @GetMapping("/list")
    @OperationLog(module = "消息管理", operation = "获取用户的消息列表")
    public Result<MessageListVO> getUserMessageList(@RequestParam Long userId) {
        try {
            MessageListVO messageVOList = messageService.getUserMessageList(userId);
            return Result.success(messageVOList);
        } catch (Exception e) {
            return Result.failure(400, e.getMessage());
        }

    }


}
