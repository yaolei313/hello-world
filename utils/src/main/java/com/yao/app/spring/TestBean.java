package com.yao.app.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaolei02 on 2017/7/29.
 */
@Component
public class TestBean {

    @Autowired
    private TempName name1;

    private TempName name2;

    public TempName getName1() {
        return name1;
    }

    public void setName1(TempName name1) {
        this.name1 = name1;
    }

    public TempName getName2() {
        return name2;
    }

    public void setName2(TempName name2) {
        this.name2 = name2;
    }
}
