package com.yao.app.jdkproxy;

import java.lang.reflect.Proxy;

public class TestProxy {

	public static void main(String[] args) {
		IHelloWorld hello = new HelloWorldImpl();
		IHelloWorld helloProxy = (IHelloWorld) Proxy.newProxyInstance(hello
				.getClass().getClassLoader(), hello.getClass().getInterfaces(),
				new Watcher(hello));

		helloProxy.sayHello();
	}

}
