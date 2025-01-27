package com.example.tipovacka.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "AaPlxTVyvyekDsQ8CD5EJ4oV"; // Tajný klíč (změň na bezpečnější)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hodin

    // Generování JWT tokenu
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Získání uživatelského jména z tokenu
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Validace tokenu
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    // Kontrola, zda token expiroval
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Získání dat (claims) z tokenu
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
