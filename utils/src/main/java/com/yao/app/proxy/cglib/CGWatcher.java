package com.yao.app.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGWatcher implements MethodInterceptor {

	private Object target;

	public CGWatcher(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println(obj.getClass());
		
		System.out.println("鼓掌 pa pa pa");
		Object result = method.invoke(target, args);
		System.out.println("鼓掌 pa pa pa");

		return result;
	}

}
