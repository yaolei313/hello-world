package com.yao.app.guice;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public interface BillingService {
    void chargeOrder(Order order, CreditCard creditCard);
}
