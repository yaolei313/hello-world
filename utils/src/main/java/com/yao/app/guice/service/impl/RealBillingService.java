package com.yao.app.guice.service.impl;

import com.google.inject.Inject;
import com.yao.app.guice.model.ChargeResult;
import com.yao.app.guice.model.CreditCard;
import com.yao.app.guice.model.Order;
import com.yao.app.guice.model.Receipt;
import com.yao.app.guice.service.BillingService;
import com.yao.app.guice.service.CreditCardService;
import com.yao.app.guice.service.TransactionLogService;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class RealBillingService implements BillingService {

    private final CreditCardService processor;
    private final TransactionLogService transactionLog;

    @Inject
    public RealBillingService(CreditCardService processor,
        TransactionLogService transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }

    @Override
    public Receipt chargeOrder(Order order, CreditCard creditCard) {
        try {
            ChargeResult result = processor.charge(creditCard, order.getAmount());
            transactionLog.logChargeResult(result);

            return result.isSuccess()
                ? Receipt.forSuccessfulCharge(order.getAmount())
                : Receipt.forDeclinedCharge(result.getDeclineMessage());
        } catch (Exception e) {
            transactionLog.logConnectException(e);
            return Receipt.forSystemFailure(e.getMessage());
        }
    }
}
