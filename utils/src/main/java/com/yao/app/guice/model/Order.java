package com.yao.app.guice.model;

import java.math.BigDecimal;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class Order {

    private long orderId;

    private BigDecimal amount;

    public Order(long orderId, BigDecimal amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
