package com.yao.app.spring;

/**
 * Created by yaolei02 on 2017/7/29.
 */
@MyAlias(value = "aa")
public class TempName {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
