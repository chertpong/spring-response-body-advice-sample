package com.kritacademy.spring.sample;

import java.lang.annotation.*;

/**
 * @author krit on 7/10/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RestFilter {
}
