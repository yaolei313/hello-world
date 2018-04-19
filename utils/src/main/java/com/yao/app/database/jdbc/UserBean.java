package com.yao.app.database.jdbc;

import java.util.Date;

/**
 * 用户对象bean
 *
 * @author summer
 */
public class UserBean {

    private Long id;

    /**
     * 注册用户名
     */
    private String username;

    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * gravatar邮件地址
     */

    // 需要占位时加上
    // @XmlElement(nillable=true)
    private String gravatarMail;

    /**
     * 注册时间
     */
    private Date registerTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGravatarMail() {
        return gravatarMail;
    }

    public void setGravatarMail(String gravatarMail) {
        this.gravatarMail = gravatarMail;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserBean{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", gravatarMail='").append(gravatarMail).append('\'');
        sb.append(", registerTime=").append(registerTime);
        sb.append('}');
        return sb.toString();
    }
}
