package com.yao.app.springmvc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.yao.app.springmvc.util.CustomDateAdapter;

/**
 * 用户对象bean
 * 
 * @author summer
 */
@XmlRootElement
@XmlAccessorType
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	/**
	 * 注册用户名
	 */
	private String name;

	/**
	 * 注册邮箱
	 */
	private String email;

	/**
	 * gravatar邮件地址
	 */
	private String gravatarMail;

	/**
	 * 注册时间
	 */
	private Date registerTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// 需要占位时加上
	// @XmlElement(nillable=true)
	public String getGravatarMail() {
		return gravatarMail;
	}

	public void setGravatarMail(String gravatarMail) {
		this.gravatarMail = gravatarMail;
	}

	@XmlJavaTypeAdapter(CustomDateAdapter.class)
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

}
