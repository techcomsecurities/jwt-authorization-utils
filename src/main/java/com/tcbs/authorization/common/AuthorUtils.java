package com.tcbs.authorization.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tcbs.authorization.common.Constants.BEARER;

public class AuthorUtils {
    static Logger logger = LoggerFactory.getLogger(AuthorUtils.class);

    public static Optional<String> getToken(HttpHeaders headers) {
        List<String> tokens = headers.get(Constants.AUTHORIZATION);
        if(tokens != null && tokens.size() > 0) {
            return Optional.ofNullable(tokens.get(0)); // get first
        } else return Optional.empty();
    }

    public static Optional<Map<String, Claim>> getJwtClaims(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return Optional.ofNullable(jwt.getClaims());
        } catch (JWTDecodeException exception){
            logger.error("token invalid {}", exception);
            return Optional.empty();
        }
    }

    public static String normalizeBearer(String authorValue) {
        String regex = String.format("(%s)\\s(.*)", BEARER);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(authorValue);

        if(matcher.find()) return matcher.group(2);
        else return authorValue;
    }
}
