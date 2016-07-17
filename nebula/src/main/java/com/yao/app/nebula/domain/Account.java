package com.yao.app.nebula.domain;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class Account {

	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
