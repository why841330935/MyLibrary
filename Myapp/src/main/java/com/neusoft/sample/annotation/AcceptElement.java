package com.neusoft.sample.annotation;

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
public @interface AcceptElement {
    AcceptScheduler acceptScheduler() default AcceptScheduler.MAIN_THREAD;

    AcceptType[] value() default {};
}
