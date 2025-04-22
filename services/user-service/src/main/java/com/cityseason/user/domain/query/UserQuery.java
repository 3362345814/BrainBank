package com.cityseason.user.domain.query;

import com.cityseason.common.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户查询参数")
public class UserQuery extends PageQuery {


    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "用户类型")
    private Integer role;

    @Schema(description = "最近登录时间最小值")
    private LocalDateTime lastLoginAtMin;

    @Schema(description = "最近登录时间最大值")
    private LocalDateTime lastLoginAtMax;


}
