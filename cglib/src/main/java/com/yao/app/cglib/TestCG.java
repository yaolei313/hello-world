package com.yao.app.cglib;

import java.io.Serializable;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class TestCG {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloImpl.class);
		enhancer.setInterfaces(new Class[] { Serializable.class });
		//enhancer.setCallbacks(new Callback[]{new CGWatcher(new HelloImpl())});
		enhancer.setCallbacks(new Callback[]{new CGWatcher(new HelloImpl()),NoOp.INSTANCE});
		enhancer.setCallbackFilter(new CGFilter());

		HelloImpl hello = (HelloImpl) enhancer.create();
		Class<?> cl = hello.getClass();

		System.out.println("--------------------");
		printClasses(cl.getSuperclass());
		System.out.println("--------------------");
		printClasses(cl.getInterfaces());
		System.out.println("--------------------");

		hello.sayHello();
		
		//hello.sayHelloByBoss();
		
		//hello.sayHelloByLeader();
	}

	private static void printClasses(Class<?>... cls) {
		for (Class<?> cl : cls) {
			System.out.print(cl.getName() + "\t");
		}
		System.out.println();
	}

}
