package com.example.tipovacka.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;
import io.jsonwebtoken.Claims;

public class JwtInterceptor implements HandlerInterceptor {
    
    private final JwtConfig jwtConfig;
    
    public JwtInterceptor(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String authHeader = request.getHeader("Authorization");
        
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        
        if (request.getRequestURI().contains("/api/players/login") ||
            request.getRequestURI().contains("/api/players/create-admin")) {
            return true;
        }
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        String token = authHeader.substring(7);
        try {
            Claims claims = jwtConfig.validateToken(token);
            String role = claims.get("role", String.class);
            
            // Admin má přístup všude
            if ("ADMIN".equals(role)) {
                return true;
            }
            
            // USER má přístup jen k některým endpointům
            if ("USER".equals(role)) {
                return isUserAllowedEndpoint(request);
            }
            
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    private boolean isUserAllowedEndpoint(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        
        // GET endpointy pro týmy jsou přístupné všem přihlášeným
        if (uri.startsWith("/api/teams") && "GET".equals(method)) {
            return true;
        }
        
        // Vlastní profil může USER číst a upravovat
        if (uri.startsWith("/api/players/")) {
            String userId = uri.substring("/api/players/".length());
            String tokenUserId = jwtConfig.getUserIdFromToken(request.getHeader("Authorization").substring(7));
            return userId.equals(tokenUserId) && ("GET".equals(method) || "PUT".equals(method));
        }
        
        return false;
    }
} 