package com.example.libaop.annotation.polling;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Polling {
    int expiry() default -1; // 过期时间,单位是秒
}
