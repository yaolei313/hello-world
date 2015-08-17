package com.yao.app.nebula.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

@Aspect
public class TraceAdvice implements Ordered{

    private Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("within(com.yao.app.nebula.service..*) && @annotation(com.yao.app.nebula.util.TraceAvailable)")
    private void anyMethod() {}

    /**
     * http://www.blogjava.net/supercrsky/articles/174368.html
     * 
     * @param jp
     * @param annotation
     * @return
     * @throws Throwable
     */
    @Around(value = "anyMethod() && @annotation(annotation)", argNames="annotation")
    public Object around(ProceedingJoinPoint jp, TraceAvailable annotation) throws Throwable {
        log.info("get condition");
        
        log.info(jp.getSignature().getDeclaringTypeName());
        log.info(jp.getSignature().getName());
        log.info(jp.getSignature().getClass().toGenericString());
        log.info(jp.getSignature().getClass().toString());
        log.info(jp.getSignature().getDeclaringType().getName());
        log.info(jp.getSignature().getDeclaringType().getSimpleName());
        log.info(jp.getSignature().getModifiers()+"");
        
        // getThis返回类似com.sun.proxy.$Proxy24的对象
        log.info(jp.getThis().getClass().getName());
        
        log.info(jp.getTarget().getClass().getCanonicalName());
        log.info(jp.getTarget().getClass().getSimpleName());
        log.info(jp.getTarget().getClass().getTypeName());
        //log.info(jp.getThis().getClass().get);
        
        log.info(jp.toString());
        log.info(jp.toShortString());
        log.info(jp.toLongString());
        log.info(jp.getThis().toString());
        log.info(jp.getTarget().toString());
        log.info(jp.getArgs().toString());
        log.info(jp.getKind());

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

    @Override
    public int getOrder() {
        // 值较低的那个有更高的优先级,控制多个切面在同一个连接点中运行时的顺序
        return 0;
    }
    
 
}
