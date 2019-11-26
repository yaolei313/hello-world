package com.yao.app.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-11-19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface MyAlias {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
