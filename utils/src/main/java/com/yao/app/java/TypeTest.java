package com.yao.app.java;

import java.util.List;

public class TypeTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Integer t1 = new Integer(199800);
        Integer t2 = new Integer(199800);
        System.out.println(t1 == t2);

        System.out.println("------------");

        // cache range [-128, 127]
        Integer t3 = new Integer(127);
        Integer t4 = new Integer(127);
        System.out.println(t3 == t4);
        System.out.println(127 == t4);
        System.out.println(t3 == 127);

        Integer t5 = new Integer(128);
        Integer t6 = new Integer(128);
        System.out.println(t5 == t6);
        System.out.println(128 == t4);
        System.out.println(t3 == 128);

        System.out.println("-----tttt-------");

        printlnClazz(byte.class);
        printlnClazz(byte[].class);
        printlnClazz(Byte.class);
        printlnClazz(Byte[].class);
        printlnClazz(List.class);

        System.out.println();
    }

    private static void printlnClazz(Class clazz){
        // http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.3
        System.out.println("------------");
        System.out.println(clazz.getCanonicalName());
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getName());
        System.out.println(clazz.getTypeName());
    }
}
