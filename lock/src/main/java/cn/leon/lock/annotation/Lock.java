package cn.leon.lock.annotation;

import cn.leon.lock.enums.LockTypeEnum;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Lock {
    LockTypeEnum type();

    long waitTime() default -1;

    long leaseTime() default -1;
}
