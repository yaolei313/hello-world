package com.yao.app.springmvc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户对象bean
 * 
 * @author summer
 */
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
    private Date regiterTime;

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

    public String getGravatarMail() {
        return gravatarMail;
    }

    public void setGravatarMail(String gravatarMail) {
        this.gravatarMail = gravatarMail;
    }

    public Date getRegiterTime() {
        return regiterTime;
    }

    public void setRegiterTime(Date regiterTime) {
        this.regiterTime = regiterTime;
    }

}
