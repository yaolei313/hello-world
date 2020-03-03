package com.yao.app.java.types;

import java.lang.reflect.Method;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-03-03
 */
public class BaseStudy {

    public static void main(String[] args) {
        Method[] methods = MyInterfaceImpl.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            System.out.println(method.getName() + "/" + method.getDeclaringClass());
        }
    }
}
