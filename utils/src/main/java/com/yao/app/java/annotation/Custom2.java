package com.yao.app.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
