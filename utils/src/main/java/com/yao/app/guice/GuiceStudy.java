package com.yao.app.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yao.app.guice.model.CreditCard;
import com.yao.app.guice.model.Order;
import com.yao.app.guice.service.BillingService;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class GuiceStudy {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BillingModule());
        BillingService billingService = injector.getInstance(BillingService.class);

        Order order = new Order(123, BigDecimal.valueOf(100));
        CreditCard creditCard = new CreditCard(1234, LocalDate.of(2020, 11, 1));
        billingService.chargeOrder(order, creditCard);
    }
}
