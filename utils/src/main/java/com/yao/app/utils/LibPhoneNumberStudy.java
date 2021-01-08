package com.yao.app.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class LibPhoneNumberStudy {

    public static void main(String[] args) {
        String str = "+8612700006138";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            PhoneNumber result = phoneUtil.parse(str, "CH");

            System.out.println(result.getCountryCode());
            System.out.println(result.getNationalNumber());
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(86);
        phoneNumber.setNationalNumber(12700006138L);
        boolean result = phoneUtil.isValidNumber(phoneNumber);
        System.out.println(result);
    }
}
