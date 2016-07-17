package com.yao.app.proxy.jdk;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        IHelloWorld hello = new HelloWorldImpl();
        IHelloWorld helloProxy = (IHelloWorld) Proxy.newProxyInstance(hello.getClass().getClassLoader(),
                hello.getClass().getInterfaces(), new Watcher(hello));

        Class<?> clazz = hello.getClass();

        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Watcher(hello));

        helloProxy.sayHello();
        ((INamed) obj).getName();
    }
}
