package com.yao.app.guice;

import com.google.inject.AbstractModule;
import com.yao.app.guice.service.BillingService;
import com.yao.app.guice.service.CreditCardService;
import com.yao.app.guice.service.TransactionLogService;
import com.yao.app.guice.service.impl.DatabaseTransactionLogService;
import com.yao.app.guice.service.impl.PaypalCreditCardService;
import com.yao.app.guice.service.impl.RealBillingService;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class BillingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TransactionLogService.class).to(DatabaseTransactionLogService.class);
        bind(CreditCardService.class).to(PaypalCreditCardService.class);
        bind(BillingService.class).to(RealBillingService.class);
    }
}
