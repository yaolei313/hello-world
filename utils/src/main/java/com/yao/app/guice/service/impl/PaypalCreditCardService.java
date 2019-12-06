package com.yao.app.guice.service.impl;

import com.yao.app.guice.model.ChargeResult;
import com.yao.app.guice.model.CreditCard;
import com.yao.app.guice.service.CreditCardService;
import java.math.BigDecimal;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class PaypalCreditCardService implements CreditCardService {

    @Override
    public ChargeResult charge(CreditCard creditCard, BigDecimal amount) {
        System.out.println("paypal");
        return null;
    }
}
