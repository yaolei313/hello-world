package com.yao.app.guice.service;

import com.yao.app.guice.model.CreditCard;
import com.yao.app.guice.model.Order;
import com.yao.app.guice.model.Receipt;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public interface BillingService {

    Receipt chargeOrder(Order order, CreditCard creditCard);
}
