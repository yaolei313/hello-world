package com.yao.app.guice.service;

import com.yao.app.guice.model.ChargeResult;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public interface TransactionLogService {

    void logChargeResult(ChargeResult result);

    default void logConnectException(Exception e) {

    }
}
