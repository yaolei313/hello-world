package com.yao.app.proxy.cglib;

import com.yao.app.proxy.jdk.INamed;

public class HelloImpl implements INamed {
	public void sayHello() {
		System.out.println("lei: hello world");
		sayHelloByBoss();
	}
	
	public void sayHelloByBoss() {
		System.out.println("boss : hello boy");
	}
	
	public void sayHelloByLeader() {
		System.out.println("leader : hello guy");
	}

    @Override
    public String getName() {
        System.out.println("cglib li bai");
        return "cglib li bai";
    }
}
