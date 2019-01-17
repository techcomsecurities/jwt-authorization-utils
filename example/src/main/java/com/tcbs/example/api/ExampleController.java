package com.tcbs.example.api;

import com.tcbs.authorization.annotation.JWTAccessToken;
import com.tcbs.authorization.annotation.JWTAuthorization;
import com.tcbs.authorization.common.Constants;
import com.tcbs.authorization.model.JWTAccessClaims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExampleController {

    @RequestMapping("/hello")
    @JWTAuthorization(failIfTokenNotPresent = true, enableTokenVerifying = false)
    public ResponseEntity<?> sayHi(
            @RequestHeader HttpHeaders headers,
            @JWTAccessToken JWTAccessClaims accessUser,
            @JWTAccessToken(headerKey = Constants.API_KEY) JWTAccessClaims serviceKey) {
        if(serviceKey == null) {
            if(accessUser == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("token not match pre-define assumption");
            } else {
                return ResponseEntity.ok("Hello iss: " + accessUser.getClaimValue("iss", String.class) + " !");
            }
        } else {
            return ResponseEntity.ok("Hello sub: " + accessUser.getClaimValue("sub", String.class) + " !");
        }
    }
}
