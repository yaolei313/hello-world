package com.yao.app.java.classloader;

import com.yao.app.map.Student;

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        System.out.println(ClassLoaderTest.class.getName());

        ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader newClassLoader = new CustomClassLoader();

        System.out.println(appClassLoader);
        System.out.println(appClassLoader);
        System.out.println(newClassLoader);

        Class<?> class1 = Class.forName("org.mariadb.jdbc.Driver", false, sysClassLoader);
        System.out.println(class1.getClassLoader());

        Student s = new Student();
        System.out.println(s.getClass().getClassLoader());

        System.out.println("----1------");
        try {
            // initialize=false，Notify的static代码块不会被执行，不会有异常抛出
            Class clazz = Class.forName("com.yao.app.java.classloader.Notify", false, ClassLoaderTest.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("----2------");
        try {
            Notify test2 = new Notify();
            test2.sendMessage();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("----3------");
        try {
            // initialize=false，Notify的static代码块会被执行，有异常会导致加载失败。
            Class.forName("com.yao.app.java.classloader.Notify", true, ClassLoaderTest.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
