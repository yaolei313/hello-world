package com.yao.app.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Watcher implements InvocationHandler {
    private Object target;

    public Watcher(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("watching before method execute");
        Object obj = method.invoke(target, args);
        System.out.println("watching after method execute");

        return obj;
    }

}
