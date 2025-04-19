package com.cityseason.user.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 林心海
 * @since 2025-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wallet")
public class Wallet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 钱包ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 余额
     */
    @TableField("balance")
    private BigDecimal balance;

    /**
     * 可提现余额
     */
    @TableField("withdrawable_balance")
    private BigDecimal withdrawableBalance;

    /**
     * 总收入
     */
    @TableField("total_income")
    private BigDecimal totalIncome;

    /**
     * 总支出
     */
    @TableField("total_expense")
    private BigDecimal totalExpense;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;


}
