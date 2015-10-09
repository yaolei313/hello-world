package com.yao.app.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class CGFilter implements CallbackFilter {

	@Override
	public int accept(Method method) {
		
		String name = method.getName();
		if("sayHelloByBoss".equals(name)){
			// 对应
			return 0;
		}
		
		// 对应NoOp
		return 1;
	}

}
