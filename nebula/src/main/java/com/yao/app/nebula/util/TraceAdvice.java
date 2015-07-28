package com.yao.app.nebula.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class TraceAdvice {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("within(com.yao.app.nebula.service..*) && @annotation(com.yao.app.nebula.util.TraceAvailable)")
    private void anyMethod() {}

    @Around(value = "anyMethod() && @annotation(annotation)")
    public Object around(ProceedingJoinPoint jp, TraceAvailable annotation) throws Throwable {
        log.info("get condition");
        
        log.info(jp.getSignature().getDeclaringTypeName());
        log.info(jp.getSignature().getName());
        log.info(jp.getSignature().getClass().toGenericString());
        log.info(jp.getSignature().getClass().toString());
        log.info(jp.getSignature().getDeclaringType().getName());
        log.info(jp.getSignature().getDeclaringType().getSimpleName());
        log.info(jp.getSignature().getModifiers()+"");
        
        log.info(jp.getThis().getClass().getName());
        log.info(jp.getThis().getClass().getCanonicalName());
        log.info(jp.getThis().getClass().getSimpleName());
        log.info(jp.getThis().getClass().getTypeName());
        //log.info(jp.getThis().getClass().get);
        
        jp.getTarget().getClass();

        Object[] args = jp.getArgs();


        if (annotation == null || !annotation.enabled()) {
            log.info("check condition failed");
            return jp.proceed(args);
        }
        log.info("check condition passed");

        log.info(annotation.desc());

        Object object = null;
        try {
            object = jp.proceed(args);
        } finally {
        }

        log.info("call traceutil finished");

        return object;
    }
    
 
}
