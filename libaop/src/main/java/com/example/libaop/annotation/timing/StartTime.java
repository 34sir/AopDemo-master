package com.example.libaop.annotation.timing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD,CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartTime {
}
