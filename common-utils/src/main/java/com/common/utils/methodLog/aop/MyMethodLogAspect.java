package com.common.utils.methodLog.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class MyMethodLogAspect {

    @Resource(name="MyMethodPerformanceAspect")
    private MyMethodPerformanceAspect aspect ;

    public static final Logger LOGGER = LoggerFactory.getLogger(MyMethodLogAspect.class);

    @Pointcut("execution(public * com.common.utils.controller..*.*(..)) "
                + "|| execution(public * com.common.utils.service..*.*(..)) "
//                + "|| execution(public * com.zhexinit.odo.log.service..*.*(..)) "
//                + "|| execution(public * com.zhexinit.odo.app.dao..*.*(..)) "
//                + "|| execution(public * com.zhexinit.odo.app.config.RedisClient.*(..)) "
            //+ "|| execution(public * edu.hziee.cap.common.MemCached.put(..)) "
            //+ "|| execution(public * edu.hziee.cap.common.MemCached.get(..)) "
            + "")
    private void log() {
        LOGGER.info("enter");
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint jp) {
        Object result = null;
        try {
            result= aspect.doAround(jp);
        } catch (Throwable e) {
            LOGGER.error("Any error msg that u want to write",e);
        }
        LOGGER.info("enter");
        return result;
    }
    @AfterThrowing(pointcut = "log()", throwing = "ex")
    public void doThrowing(JoinPoint jp, Throwable ex) {
        try {
            aspect.doThrowing(jp, ex);
        } catch (Throwable e) {
            LOGGER.error("Any error msg that u want to write",e);
        }
    }

}
