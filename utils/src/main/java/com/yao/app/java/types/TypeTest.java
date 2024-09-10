package com.yao.app.java.types;

import java.util.List;

public class TypeTest {

    private volatile int i = 0;

    public static void main(String[] args) {
        String a = new String("abc");
        System.out.println(a);

        String b= "efg";
        System.out.println(b);

        String c = new String(b);
        System.out.println(c == b);

        TypeTest test = new TypeTest();
        test.i++;

        Integer t1 = new Integer(199800);
        Integer t2 = new Integer(199800);
        System.out.println(t1 == t2);

        System.out.println("------------");

        // cache range [-128, 127]
        Integer t3 = Integer.valueOf(127);
        Integer t4 = Integer.valueOf(127);
        System.out.println(t3 == t4);
        System.out.println(127 == t4);
        System.out.println(t3 == 127);
        // 编译后
        // System.out.println(127 == t4.intValue());
        // System.out.println(t3.intValue() == 127);

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
