package com.yao.app.java;

/**
 * Created by yaolei02 on 2018/3/9.
 */
public class Notify {
    private static final String some;

    static {
        some = initSomething();
    }

    public Notify() {
    }

    public void sendMessage(){
        System.out.println("hello");
    }

    public static String initSomething(){
        throw new RuntimeException("xxx");
    }
}
