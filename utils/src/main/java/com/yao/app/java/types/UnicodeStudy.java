package com.yao.app.java.types;

import java.lang.Character.UnicodeBlock;

public class UnicodeStudy {

    public static void main(String[] args) {
        String a = "\uD83C\uDDED\uD83C\uDDF0"; // utf-16编码,0xD800-0xDFFF对应代理区
        System.out.println(a); // 4length 8bit

        System.out.println(Character.codePointAt(a, 0));// 正确127469
        System.out.println(Character.charCount(Character.codePointAt(a, 0)));// 2
        System.out.println(Character.codePointAt(a, 2));// 正确127472
        System.out.println(Character.charCount(Character.codePointAt(a, 2)));// 2

        System.out.println(Character.toChars(127469));
        System.out.println(Character.toChars(127472));

        System.out.println(UnicodeBlock.of(127469));
    }
}
