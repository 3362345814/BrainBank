package com.cityseason.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cityseason.user.domain.dto.MessageDTO;
import com.cityseason.user.domain.po.Message;
import com.cityseason.user.domain.vo.MessageListVO;
import com.cityseason.user.domain.vo.MessageVO;
import jakarta.validation.Valid;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林心海
 * @since 2025-04-25
 */
public interface IMessageService extends IService<Message> {

    MessageVO sendSystemMessage(@Valid MessageDTO messageDTO, Long[] userIds);

    MessageVO sendCreatorMessage(@Valid MessageDTO messageDTO);

    MessageVO sendSystemMessageToRole(@Valid MessageDTO messageDTO, Integer userRole);

    MessageListVO getUserMessageList(Long userId);
}
