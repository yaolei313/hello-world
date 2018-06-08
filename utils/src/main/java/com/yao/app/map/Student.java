package com.yao.app.map;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    public static int value = 123;

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
