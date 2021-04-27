package cn.leon.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
/**
 * 自定义注解
 *  @Retention 保留注释的各种策略，它们与元注释(@Retention)一起指定注释要保留多长时间
 *  @Target 注释可能出现在Java程序中的语法位置（这些常量与元注释类型(@Target)一起指定在何处写入注释的合法位置）
 *  @Documented 注释了文档化，它的注释成为公共API的一部分。
 */
public @interface leonBean {
    public abstract String value() default "";
}
