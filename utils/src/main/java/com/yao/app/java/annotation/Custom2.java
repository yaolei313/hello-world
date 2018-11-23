package com.yao.app.java.annotation;

import java.lang.annotation.*;

/**
 * Created by yaolei02 on 2018/11/23.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Documented
@Inherited
public @interface Custom2 {
    String value() default "456";
}
