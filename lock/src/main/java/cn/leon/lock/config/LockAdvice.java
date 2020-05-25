package cn.leon.lock.config;

import cn.leon.lock.model.SelfLock;
import cn.leon.lock.model.LockProperties;
import com.google.common.collect.Sets;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;

import java.util.Set;

public class LockAdvice extends AbstractAutoProxyCreator implements InitializingBean {

    private MethodInterceptor interceptor;
    private LockProperties properties;
    private SelfLock selfLock;
    private String applicationName;
    private final Set<String> set = Sets.newHashSet();

    public LockAdvice( LockProperties properties, SelfLock selfLock,String applicationName) {
        this.properties = properties;
        this.selfLock = selfLock;
        this.applicationName = applicationName;
    }

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        return super.wrapIfNecessary(bean, beanName, cacheKey);
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String s, TargetSource targetSource) throws BeansException {
        return new Object[0];
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
