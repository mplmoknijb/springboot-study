package com.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Order(2)
public class LogAspect {
    public LogAspect() {
    }

    @Pointcut("execution(public * com.boot.controller.*.*(..))")
    public void weblog() {
    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            returning = "ret",
            pointcut = "weblog()"
    )
    public void doAfterReturning(Object ret) {
        System.out.println("方法的返回值 : " + ret);
    }

    @AfterThrowing("weblog()")
    public void throwss(JoinPoint jp) {
        System.out.println("方法异常时执行.....");
    }

    @After("weblog()")
    public void after(JoinPoint jp) {
        System.out.println("方法最后执行.....");
    }

    @Around("weblog()")
    public Object arround(ProceedingJoinPoint pjp) {
        System.out.println("方法环绕start.....");

        try {
            Object o = pjp.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable var3) {
            var3.printStackTrace();
            return null;
        }
    }
}

