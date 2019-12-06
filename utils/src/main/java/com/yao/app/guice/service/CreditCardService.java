package com.yao.app.guice.service;

import com.yao.app.guice.model.ChargeResult;
import com.yao.app.guice.model.CreditCard;
import java.math.BigDecimal;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public interface CreditCardService {

    ChargeResult charge(CreditCard creditCard, BigDecimal amount);
}
