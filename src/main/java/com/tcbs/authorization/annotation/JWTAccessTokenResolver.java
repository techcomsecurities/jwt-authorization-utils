package com.tcbs.authorization.annotation;

import com.auth0.jwt.interfaces.Claim;
import com.tcbs.authorization.common.AuthorUtils;
import com.tcbs.authorization.model.JWTAccessClaims;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.Optional;

public class JWTAccessTokenResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(JWTAccessToken.class) != null &&
                JWTAccessClaims.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) throws Exception {

        System.out.println("=============JWTAccessTokenResolver=================");

        if (JWTAccessClaims.class.isAssignableFrom(methodParameter.getParameterType())) {
            JWTAccessToken ann = methodParameter.getParameterAnnotation(JWTAccessToken.class);
            if(ann == null) return null;

            String header = ann.headerKey();

            String[] accessTokens = nativeWebRequest.getHeaderValues(header);
            if (accessTokens != null && accessTokens.length > 0) {
                String jwtToken = AuthorUtils.normalizeBearer(accessTokens[0]);
                Optional<Map<String, Claim>> claimsOpt = AuthorUtils.getJwtClaims(jwtToken);
                return claimsOpt.map(claims -> JWTAccessClaims.builder()
                        .claims(claims)
                        .build())
                        .orElse(null);
            }
        }

        return null;
    }
}
