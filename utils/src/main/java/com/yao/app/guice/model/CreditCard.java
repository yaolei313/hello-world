package com.yao.app.guice.model;

import java.time.LocalDate;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-06
 */
public class CreditCard {

    private long cardNumber;

    private LocalDate expirationDate;

    public CreditCard(long cardNumber, LocalDate expirationDate) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
