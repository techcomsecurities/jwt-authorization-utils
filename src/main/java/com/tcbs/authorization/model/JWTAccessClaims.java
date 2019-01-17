package com.tcbs.authorization.model;

import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTAccessClaims {
    private Map<String, Claim> claims;
    public <T> T getClaimValue(String claimName, Class<T> type) {
        if(claims != null && claims.containsKey(claimName)) return claims.get(claimName).as(type);
        else return null;
    }
}
