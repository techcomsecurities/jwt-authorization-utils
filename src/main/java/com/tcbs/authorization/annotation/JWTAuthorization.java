package com.tcbs.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JWTAuthorization {
    String headersParamName() default "headers";
    boolean failIfTokenNotPresent() default true;
    boolean enableTokenVerifying() default false;
}
