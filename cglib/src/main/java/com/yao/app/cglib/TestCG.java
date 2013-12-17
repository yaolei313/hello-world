package com.yao.app.cglib;

import java.io.Serializable;

import net.sf.cglib.proxy.Enhancer;

public class TestCG {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloImpl.class);
		//enhancer.setInterfaces(new Class[] { Serializable.class });
		enhancer.setCallback(new CGWatcher(new HelloImpl()));

		HelloImpl hello = (HelloImpl) enhancer.create();
		Class<?> cl = hello.getClass();

		System.out.println("--------------------");
		printClasses(cl.getSuperclass());
		System.out.println("--------------------");
		printClasses(cl.getInterfaces());
		System.out.println("--------------------");

		hello.sayHello();
	}

	private static void printClasses(Class<?>... cls) {
		for (Class<?> cl : cls) {
			System.out.print(cl.getName() + "\t");
		}
		System.out.println();
	}

}
