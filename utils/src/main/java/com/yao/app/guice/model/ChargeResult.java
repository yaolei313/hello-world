package com.yao.app.guice.model;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class ChargeResult {

    private boolean success;

    private String declineMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDeclineMessage() {
        return declineMessage;
    }

    public void setDeclineMessage(String declineMessage) {
        this.declineMessage = declineMessage;
    }
}
