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
    /**
     * fetch proxy target
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public Class findTargetClass(Object proxy) throws Exception {
        if (proxy == null) {
            return null;
        }
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy.getClass();
        }
        AdvisedSupport advisedSupport = getProxyTarget(proxy);
        Object target = advisedSupport.getTargetSource().getTarget();
        return target.getClass();

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
}
