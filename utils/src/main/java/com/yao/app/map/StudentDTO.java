package com.yao.app.map;

import java.io.Serializable;

public class StudentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String stuName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

}
