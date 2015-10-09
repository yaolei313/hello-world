package com.yao.app.jdkproxy;

public class HelloWorldImpl implements IHelloWorld {

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

}
