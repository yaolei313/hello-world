package com.yao.app.proxy.jdk;

public class HelloWorldImpl implements IHelloWorld, INamed {

    @Override
    public void sayHello() {
        System.out.println("hello world, yao");
        sayHelloByBoss();
    }

    @Override
    public void sayHelloByBoss() {
        System.out.println("boss : hello boy");
    }

    @Override
    public void sayHelloByLeader() {
        System.out.println("leader : hello guy");
    }

    @Override
    public String getName() {
        System.out.println("li bai");
        return "Li bai";
    }

}
