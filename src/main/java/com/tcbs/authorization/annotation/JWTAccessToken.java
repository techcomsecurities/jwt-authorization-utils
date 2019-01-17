package com.tcbs.authorization.annotation;

import com.tcbs.authorization.common.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface JWTAccessToken {
    String headerKey() default Constants.AUTHORIZATION;
}
