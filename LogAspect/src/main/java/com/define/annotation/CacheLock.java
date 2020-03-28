package com.define.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {
    //redis锁前缀
    String prefix() default "";
    //redis锁过期时间
    int expire() default 5;
    //redis锁过期时间单位
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    //redis  key分隔符
    String delimiter() default ":";
}