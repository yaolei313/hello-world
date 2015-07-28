package com.yao.app.nebula.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author lei.yao
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TraceAvailable {
    /**
     * @return
     */
    String desc() default "";
    
    /**
     * 是否启用
     * 
     * @return
     */
    boolean enabled() default true;
}
