package com.yao.app.java.types;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 参照Class.getEnclosingClass的注释
 * There are five kinds of classes (or interfaces):
 * a) Top level classes
 * b) Nested classes (static member classes)
 * c) Inner classes (non-static member classes)
 * d) Local classes (named classes declared within a method)
 * e) Anonymous classes
 *
 *
 * JVM Spec 4.7.7: A class must have an EnclosingMethod
 * attribute if and only if it is a local class or an anonymous class.
 *
 * @author allen@xiaohongshu.com 2019-12-03
 */
public class TopLevelClass {

    public class InnerClass {

    }

    public static class NestedClass {

    }

    public static void main(String[] args) {
        class LocalClass {

        }
        List<String> list = new ArrayList<>();
        // 下边就是匿名类
        list.forEach((item) -> System.out.println("anonymous class method"));
    }
}
