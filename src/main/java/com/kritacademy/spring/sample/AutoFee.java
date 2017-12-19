package com.kritacademy.spring.sample;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author krit on 12/19/2017.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoFee {
    @AliasFor("fee")
    String value() default "1";

    @AliasFor("value")
    String fee() default "1";
}
