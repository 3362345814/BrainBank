package com.cityseason.user.domain.query;

import com.cityseason.common.domain.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends PageQuery {


    private String username;

    private String phone;

    private String email;

    private Integer status;

    private Integer role;

    private LocalDateTime lastLoginAtMin;

    private LocalDateTime lastLoginAtMax;

}
