package com.yao.app.guice.model;

import java.math.BigDecimal;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class Receipt {

    public static Receipt forSuccessfulCharge(BigDecimal amount) {
        return null;
    }

    public static Receipt forDeclinedCharge(String declineMessage) {
        return null;
    }

    public static Receipt forSystemFailure(String message) {
        return null;
    }
}
