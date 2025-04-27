package com.cityseason.user.domain.vo;

import com.cityseason.user.domain.po.Message;
import com.cityseason.user.domain.po.MessageUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUserVO {


    private Message message;


    private MessageUser messageUser;


}
