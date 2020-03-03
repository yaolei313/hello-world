package com.yao.app.java.types;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-03-03
 */
public class MyInterfaceImpl implements MyInterface {

    @Override
    public void hello() {
        System.out.println("hello word");
    }

    public void hi() {
        System.out.println("hi");
    }
}
