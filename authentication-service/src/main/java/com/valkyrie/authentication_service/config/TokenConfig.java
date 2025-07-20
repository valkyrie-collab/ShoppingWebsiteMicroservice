package com.valkyrie.authentication_service.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenConfig {
    @Value("${jwts.security}")
    private String securityKey;

    private static final int EXPIRATION = 1000 * 60 * 60 * 30;

    private Key generateKey() {return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));}

    private <Instance> Instance getClaim(String token, Function<Claims, Instance> claimBearer) {
        Claims claims = Jwts.parserBuilder().setSigningKey(generateKey()).build()
                            .parseClaimsJws(token).getBody();
        return claimBearer.apply(claims);
    }

    private boolean isExpired(String token) {
        return !getClaim(token, Claims::getExpiration).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claim = new HashMap<>();
        return Jwts.builder().setClaims(claim).setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                    .signWith(generateKey()).compact();
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails userDetails){
        return userDetails.getUsername().equals(getUsername(token)) && isExpired(token);
    }
}
