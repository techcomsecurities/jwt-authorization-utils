package com.tcbs.authorization.annotation;

import com.tcbs.authorization.common.AuthorUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.OptionalInt;
import java.util.stream.IntStream;

@Aspect
@Component
public class JWTAuthorizationAspect {
    @Pointcut("@annotation(JWTAuthorization)")
    public void tcsAuthorizationAnnotation(JWTAuthorization JWTAuthorization) {
    }

    @Around("tcsAuthorizationAnnotation(JWTAuthorization)")
    public Object doSomething(ProceedingJoinPoint pjp, JWTAuthorization JWTAuthorization) throws Throwable {
        System.out.println("==================================== " + pjp.getArgs());

        // Check header values
        CodeSignature codeSignature = (CodeSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();

        String headersParamName = JWTAuthorization.headersParamName();
        OptionalInt indexOpt = IntStream.range(0, codeSignature.getParameterNames().length)
                .filter(i -> headersParamName.equals(codeSignature.getParameterNames()[i]))
                .findFirst();
        if(indexOpt.isPresent() && args != null && args.length >= indexOpt.getAsInt()) {
            HttpHeaders httpHeaders = (HttpHeaders) args[indexOpt.getAsInt()];
            if(httpHeaders != null) {
                if(AuthorUtils.getToken(httpHeaders).isPresent()) return pjp.proceed();
            }
        }

        if(JWTAuthorization.failIfTokenNotPresent()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("token not found");
        else return pjp.proceed();
    }
}
