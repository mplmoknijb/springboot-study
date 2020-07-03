package cn.leon.core.annotation;

import cn.leon.core.enums.LockTypeEnum;

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
