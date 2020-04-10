package cn.leon.account;

import lombok.Data;

@Data
public class AccountDTO {
    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 扣减金额
     */
    private Integer price;
}
