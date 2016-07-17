package com.yao.app.java.classloader;

/**
 * 内部类编译后是单独一个文件，故外部类被加载不会导致内部类被加载 
 * 方法内只有类在实例化时，会触发加载对应的class，仅定义T instance = null不会触发类加载
 * T.class.XXXmethod 同样会触发加载对应的class
 * 
 * @author yaolei
 *
 */
public class Test {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        System.out.println("breakpoint");

        Teacher t = new Teacher();
        Student s = null;
        Student.class.getName();
        Util.print();
        
        System.out.println("breakpoint");
    }

}
