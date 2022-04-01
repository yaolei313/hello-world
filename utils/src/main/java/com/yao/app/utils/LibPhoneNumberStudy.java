package com.yao.app.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class LibPhoneNumberStudy {

    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public static void main(String[] args) {
        //String str = "+8612700006138";
        //convert(str);

        String str2 = "+17671296529";
        convert(str2);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(86);
        phoneNumber.setNationalNumber(12700006138L);
        boolean result = phoneUtil.isValidNumber(phoneNumber);
        System.out.println(result);
    }

    public static void convert(String str) {
        try {
            PhoneNumber number = phoneUtil.parse(str, "DM");
            boolean possible = phoneUtil.isPossibleNumber(number);
            String e164 = phoneUtil.format(number, PhoneNumberFormat.E164);
            System.out.println(possible);
            System.out.println(e164);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
    }
}
