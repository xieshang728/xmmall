package com.xmall.controller.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author xies
 * @date 2018/3/13.
 */
@Aspect
@Component
public class ControllerAspectj {

    private Logger logger = LoggerFactory.getLogger(ControllerAspectj.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void aspectJMethod(){}

    @Around("aspectJMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("hello world");
        pjp.proceed();
        return null;
    }


}
