package com.yao.app.nebula.web.vo;

import java.io.Serializable;

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public Student(){
		System.out.println("new instance");
	}

	public String getName() {
		System.out.println("get name");
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	protected void finalize(){
		System.out.println("destory instance");
	}

}
