package com.example.tipovacka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.tipovacka.security.JwtConfig;
import com.example.tipovacka.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    private final JwtConfig jwtConfig;
    
    public SecurityConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    System.out.println("Access Denied Error: " + accessDeniedException.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write("Nemáte dostatečná oprávnění pro tento endpoint");
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    System.out.println("Authentication Error: " + authException.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write("Neplatný nebo chybějící token");
                })
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/players/login",
                    "/api/players/create-admin",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .authenticationProvider(new AuthenticationProvider() {
                @Override
                public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                    System.out.println("Authenticating user with roles: " + authentication.getAuthorities());
                    return authentication;
                }

                @Override
                public boolean supports(Class<?> authentication) {
                    return true;
                }
            });
        return http.build();
    }
} 