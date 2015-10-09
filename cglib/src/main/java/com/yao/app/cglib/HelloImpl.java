package com.yao.app.cglib;

public class HelloImpl {
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
}
