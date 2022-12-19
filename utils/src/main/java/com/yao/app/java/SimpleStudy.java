package com.yao.app.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleStudy {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$");
        Matcher matcher = pattern.matcher("1999-09-10");
        System.out.println(matcher.matches());

        matcher = pattern.matcher("1999-19-10");
        System.out.println(matcher.matches());
    }
}
