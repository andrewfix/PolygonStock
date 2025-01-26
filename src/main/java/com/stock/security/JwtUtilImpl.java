package com.stock.security;

import com.stock.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtilImpl implements JwtUtil {

    //@Value("${jwt.secret}")
    private String SECRET_KEY = "This issssssssssssssssdsd9kjlkjwelkjlekjlkejwlkjelkwjelkjwelkjwlkejlkwejlkwjeklwjelkjwelkjssssssssssss a secret key";
    private static final long EXPIRATION_TIME = 10000 * 60 * 15;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    public String generateToken(String identifier, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(identifier)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Map<String, Object> extractClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new HashMap<>(claims);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid JWT token", e);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Map<String, Object> claims = extractClaims(token);
            Long expiration = (Long) claims.get("exp");
            return expiration * 1000 > (new Date()).getTime();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Map<String, Object> claims = extractClaims(token);
        Date expiration = (Date) claims.get("exp");
        return expiration.before(new Date());
    }

}
