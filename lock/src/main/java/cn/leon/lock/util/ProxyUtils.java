package cn.leon.lock.util;

import cn.leon.lock.annotation.Lock;
import lombok.experimental.UtilityClass;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
public class ProxyUtils {

    public Class findTargetClass(Object proxy) throws Exception {
        if (proxy == null) {
            return null;
        }
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy.getClass();
        }
        return (AopUtils.isJdkDynamicProxy(proxy) ? getJdkTarget(proxy) :getCglibTarget(proxy));

    }

    private Class<?> getJdkTarget(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
        return target.getClass();
    }

    private Class<?> getCglibTarget(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object o = h.get(proxy);
        Field advised = o.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(o)).getTargetSource().getTarget();
        return target.getClass();
    }

    public boolean annotationExists(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Lock annotation = method.getAnnotation(Lock.class);
            if (annotation == null) {
                return false;
            }
        }
        return true;

    }
}
