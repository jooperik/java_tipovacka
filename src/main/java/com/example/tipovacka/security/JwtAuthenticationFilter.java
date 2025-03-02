package com.example.tipovacka.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtConfig jwtConfig;
    
    public JwtAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                  @NonNull HttpServletResponse response, 
                                  @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        System.out.println("=== JWT Filter Debug ===");
        System.out.println("Request URI: " + requestURI);
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                Claims claims = jwtConfig.validateToken(token);
                String userId = claims.getSubject();
                String role = claims.get("role", String.class);
                
                System.out.println("Token User ID: " + userId);
                System.out.println("Token Role: " + role);
                
                String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                System.out.println("Role with prefix: " + roleWithPrefix);
                
                // Kontrola rolí pro specifické endpointy
                if (requestURI.matches("/api/players/\\d+")) {  // Kontrola pro /api/players/{id}
                    Long requestedId = Long.parseLong(requestURI.substring(requestURI.lastIndexOf('/') + 1));
                    if (!roleWithPrefix.equals("ROLE_ADMIN") && !userId.equals(requestedId.toString())) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Nemáte oprávnění zobrazit tohoto hráče");
                        return;
                    }
                }
                
                if (requestURI.equals("/api/players")) {  // Kontrola pro /api/players
                    if (!roleWithPrefix.equals("ROLE_ADMIN")) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Nemáte oprávnění zobrazit seznam všech hráčů");
                        return;
                    }
                }
                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    List.of(new SimpleGrantedAuthority(roleWithPrefix))
                );
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authentication set in context: " + authentication);
                System.out.println("Principal: " + authentication.getPrincipal());
                System.out.println("Authorities: " + authentication.getAuthorities());
            } catch (Exception e) {
                System.out.println("Error in JWT filter: " + e.getMessage());
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Neplatný token nebo nedostatečná oprávnění");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
} 