package com.yao.app.guice;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class RealBillingService implements BillingService {

    private final CreditCardProcessor processor;
    private final TransactionLog transactionLog;

    public RealBillingService(CreditCardProcessor processor,
        TransactionLog transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }

    @Override
    public void chargeOrder(Order order, CreditCard creditCard) {

    }
}
