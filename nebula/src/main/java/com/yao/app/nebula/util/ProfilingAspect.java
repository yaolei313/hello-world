package com.yao.app.nebula.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.util.StopWatch;

@Aspect
public class ProfilingAspect implements Ordered{
	@Around("methodsToBeProfiled()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch(getClass().getSimpleName());
        try {
            sw.start(pjp.getSignature().getName());
            return pjp.proceed();
        } finally {
            sw.stop();
            System.out.println("haha:"+sw.prettyPrint());
        }
    }

    @Pointcut("execution(public * com.yao.app.nebula.service..*.*(..))")
    public void methodsToBeProfiled(){}

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 1;
    }
}
