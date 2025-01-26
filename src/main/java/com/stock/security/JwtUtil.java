package com.stock.security;

import java.util.Map;

public interface JwtUtil {
    String generateToken(String identifier, Map<String, Object> claims);

    Map<String, Object> extractClaims(String token);

    boolean isTokenValid(String token);

}