package com.aspect;

import com.UserAccess;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Aspect
public class UserAspect {
    public UserAspect() {
    }

    @Pointcut("@annotation(com.UserAccess)")
    public void access() {
    }

    @Before("access()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("second before");
    }

    @Around("@annotation(userAccess)")
    public Object around(ProceedingJoinPoint pjp, UserAccess userAccess) {
        System.out.println("second around:" + userAccess.desc());

        try {
            return pjp.proceed();
        } catch (Throwable var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
