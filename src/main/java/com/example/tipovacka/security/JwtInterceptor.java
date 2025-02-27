package com.example.tipovacka.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;

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
            jwtConfig.validateToken(token);
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
} 