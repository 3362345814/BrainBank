package com.cityseason.user.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 林心海
 * @since 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("seller_registration")
public class SellerRegistration implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，雪花算法
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户id，关联user.id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 身份证号
     */
    @TableField("id_number")
    private String idNumber;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 收款账户
     */
    @TableField("receiving_account")
    private String receivingAccount;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


}
