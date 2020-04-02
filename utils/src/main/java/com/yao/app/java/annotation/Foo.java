package com.yao.app.java.annotation;

import com.yao.app.java.jvm.Employee;
import com.yao.app.spi.annotation.Custom;
import java.io.Serializable;

/**
 * Created by yaolei02 on 2018/4/19.
 */
public @Custom("class123") class Foo extends @Custom("extend") Employee implements @Custom("interface") Serializable {
    public @Custom("field") String desc;

    public @Custom("construct return") Foo(@Custom("construct param") String desc) throws @Custom("construct exception") Exception {
        this.desc = desc;
    }

    public @Custom("method return") String getDesc(@Custom("method param 1")Foo other, @Custom("method param 2") String desc)
        throws @Custom("method exception") Exception {
        return desc;
    }
}
