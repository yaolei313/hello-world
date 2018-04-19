package com.yao.app.java;

/**
 * Created by yaolei02 on 2018/3/9.
 */
public class NoClassDefFoundErrorTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.yao.app.java.Notify", false, NoClassDefFoundErrorTest.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
