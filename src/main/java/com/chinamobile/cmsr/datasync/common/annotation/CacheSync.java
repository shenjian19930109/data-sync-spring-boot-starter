package com.chinamobile.cmsr.datasync.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/10 16:29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheSync {

//    @AliasFor("key")
//    String value() default "";

//    @AliasFor("value")
    String key() default "";
}
