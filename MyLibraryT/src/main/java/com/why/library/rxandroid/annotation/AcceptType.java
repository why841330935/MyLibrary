package com.why.library.rxandroid.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * on 2016/8/23.
 * Created by WangHuanyu 外包
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AcceptType {
    String tag() default "";

    Class clazz() default Object.class;
}
