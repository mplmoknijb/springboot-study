package cn.leon.core.util;

import cn.leon.core.annotation.Lock;
import lombok.experimental.UtilityClass;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.target.EmptyTargetSource;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
public class ProxyUtils {
    /**
     * fetch proxy target
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public Class<?> findTargetClass(Object proxy) throws Exception {
        if (proxy == null) {
            return null;
        }
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy.getClass();
        }
        AdvisedSupport advisedSupport = getProxyTarget(proxy);
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            TargetSource targetSource = advisedSupport.getTargetSource();
            return targetSource instanceof EmptyTargetSource ? findInterfaceByAdvised(advisedSupport)[0] : targetSource.getTargetClass();

        }
        Object target = advisedSupport.getTargetSource().getTarget();
        return findTargetClass(target);

    }

    public Class<?>[] findInterfaces(Object proxy) throws Exception {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            AdvisedSupport support = getProxyTarget(proxy);
            return findInterfaceByAdvised(support);
        }
        return null;
    }

    public Class<?>[] findInterfaceByAdvised(AdvisedSupport advisedSupport) {
        Class<?>[] interfaces = advisedSupport.getProxiedInterfaces();
        Assert.isTrue(interfaces.length == 0, "Jdk dynamic agent has no implementation class");
        return interfaces;
    }

    public AdvisedSupport getProxyTarget(Object proxy) throws Exception {
        String type = AopUtils.isJdkDynamicProxy(proxy) ? "h" : "CGLIB$CALLBACK_0";
        Field h = proxy.getClass().getDeclaredField(type);
        h.setAccessible(true);
        Object o = h.get(proxy);
        Field advised = o.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return (AdvisedSupport) advised.get(o);
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

    public boolean annotationExists(Class<?>[] clazz) {
        if (clazz == null || clazz.length == 0){
            return false;
        }
        for (Class clz : clazz) {
            if (clazz == null) {
                continue;
            }
            Method[] methods = clz.getMethods();
            for (Method method : methods) {
                Lock annotation = method.getAnnotation(Lock.class);
                if (annotation != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
