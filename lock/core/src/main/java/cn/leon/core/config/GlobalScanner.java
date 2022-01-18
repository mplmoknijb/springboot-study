package cn.leon.core.config;

import cn.leon.core.model.LockException;
import cn.leon.core.model.LockProperties;
import cn.leon.core.model.LockProxy;
import cn.leon.core.model.SelfLock;
import cn.leon.core.util.ProxyUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GlobalScanner extends AbstractAutoProxyCreator implements InitializingBean {

    private MethodInterceptor interceptor;
    private LockProperties properties;
    @SuppressWarnings("rawtypes")
    private SelfLock selfLock;
    private String applicationName;
    //标记防止重复
    private final Set<String> set = new HashSet<>();

    public GlobalScanner(LockProperties properties, SelfLock selfLock, String applicationName) {
        this.properties = properties;
        this.selfLock = selfLock;
        this.applicationName = applicationName;
    }

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        try {
            synchronized (set) {
                if (set.contains(beanName)) {
                    return bean;
                }
                // find class
                Class<?> serviceApi = ProxyUtils.findTargetClass(bean);
                Class<?>[] interfaces = ProxyUtils.findInterfaces(bean);
                // find annatation
                if (!ProxyUtils.annotationExists(new Class[]{serviceApi}) && !ProxyUtils.annotationExists(interfaces)) {
                    return bean;
                }
                // fill interceptor
                if (Objects.isNull(interceptor)) {
                    interceptor = (MethodInterceptor) new LockProxy(properties, selfLock);
                }
                // createProxy
                if (!AopUtils.isAopProxy(bean)) {
                    bean = super.wrapIfNecessary(bean, beanName, cacheKey);
                } else {
                    AdvisedSupport advisedSupport = ProxyUtils.getProxyTarget(bean);
                    Advisor[] advisors = buildAdvisors(beanName, new MethodInterceptor[]{interceptor});
                    for (Advisor advisor : advisors) {
                        advisedSupport.addAdvisor(advisor);
                    }
                }
                set.add(beanName);
                return bean;
            }
        } catch (Exception e) {
            throw new LockException(e);
        }
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String s, TargetSource targetSource) throws BeansException {
        return new Object[]{interceptor};
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
