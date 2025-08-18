package com.hit.jobandlogging.security;

import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${spring.jwt.key}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expiration.access.token}")
    private long EXPIRATION_ACCESS_TOKEN;

    @Value("${spring.jwt.expiration.refresh.token}")
    private long EXPIRATION_REFRESH_TOKEN;

    private Key getSignInKey() {
        byte[] encodedKey = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public String generateToken(UserPrincipal userPrincipal,boolean isRefresh){
        long nowMillis = new Date().getTime();
        Date now = new Date(nowMillis);

        long expirationTime = System.currentTimeMillis();
        if (isRefresh) {
            expirationTime = expirationTime + EXPIRATION_REFRESH_TOKEN*3600000;
        } else {
            expirationTime = expirationTime + EXPIRATION_ACCESS_TOKEN*3600000*24;
        }
        Date expirationDate = new Date(expirationTime);
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .claim("authorizes", userPrincipal.getAuthorities())
                .claim("email", userPrincipal.getEmail())
                .compact();
    }

    public boolean isValidToken(String token){
        try {
            Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new UnauthorizedException(ErrorMessage.Auth.INVALID_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new UnauthorizedException(ErrorMessage.Auth.EXPIRED_TOKEN);
        }
    }

    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public String extractEmail(String token){
        return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody().get("email").toString();
    }
}
