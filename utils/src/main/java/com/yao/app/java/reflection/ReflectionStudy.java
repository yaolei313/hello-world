package com.yao.app.java.reflection;

public class ReflectionStudy {

    public static void main(String[] args) {
        Test2 test = new Test2();
        test.g();
    }

    static class Test2 {
        public void g() {
            gg();
        }

        public void gg() {
            // jdk8 ok
            /*System.out.println(Reflection.getCallerClass());
            System.out.println(Reflection.getCallerClass(-1));
            System.out.println(Reflection.getCallerClass(0));
            System.out.println(Reflection.getCallerClass(1));
            System.out.println(Reflection.getCallerClass(2));
            System.out.println(Reflection.getCallerClass(3));
            System.out.println(Reflection.getCallerClass(4));
            System.out.println(Reflection.getCallerClass(5));*/

            // jdk9 ok
            //System.out.println(StackWalker.getInstance().getCallerClass());
        }

    }

}
