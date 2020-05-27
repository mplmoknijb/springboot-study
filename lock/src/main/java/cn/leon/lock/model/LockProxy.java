package cn.leon.lock.model;

import cn.leon.lock.annotation.Lock;
import cn.leon.lock.enums.LockTypeEnum;
import cn.leon.lock.strategy.LockStrategy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class LockProxy implements MethodInterceptor {
    private LockProperties lockProperties;
    private LockStrategy lockStrategy;

    public LockProxy(LockProperties lockProperties, LockStrategy lockStrategy) {
        this.lockProperties = lockProperties;
        this.lockStrategy = lockStrategy;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Class<?> targetClass = Objects.nonNull(methodInvocation.getThis()) ? AopUtils.getTargetClass(methodInvocation.getClass()) : null;
        Method mostSpecificMethod = ClassUtils.getMostSpecificMethod(methodInvocation.getMethod(), targetClass);
        final Method method = BridgeMethodResolver.findBridgedMethod(mostSpecificMethod);
        final Lock annatation = method.getAnnotation(Lock.class);
        if (Objects.nonNull(annatation) && LockTypeEnum.valueSet().contains(lockProperties.getType())) {
            String name = StringUtils.hasText(lockProperties.getPrefix()) ? lockProperties.getPrefix().concat("-").concat(annatation.type().getDes()) : annatation.type().getDes();
            LockInfo build = LockInfo.builder().leaseTime(annatation.leaseTime()).waitTime(annatation.waitTime())
                    .name(name).build();
            Object obj = lockStrategy.lock(build);
            try {
               return methodInvocation.proceed();
            } finally {
                lockStrategy.unlock(obj);
            }
        }
        return methodInvocation.proceed();
    }
}
