package com.yao.app.guice.service.impl;

import com.yao.app.guice.model.ChargeResult;
import com.yao.app.guice.service.TransactionLogService;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class DatabaseTransactionLogService implements TransactionLogService {

    @Override
    public void logChargeResult(ChargeResult result) {
        System.out.println("database log");
    }
}
