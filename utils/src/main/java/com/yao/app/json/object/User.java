package com.yao.app.json.object;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    // fastjson not support jdk 8 
    private LocalDate birthDay;
    //private Date birthDay;

    private Address address;

    private Contact contact;

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

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public User setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
