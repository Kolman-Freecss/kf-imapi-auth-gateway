package org.kolmanfreecss.kfimapiauthgateway.infrastructure.adapters.in.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@RequiredArgsConstructor
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    final JwtAuthConverter authConverter;
    
    final String[] allowedRoutes = new String[] {"/hello", "/api/v1/auth/**", "/incident/actuator/**"};

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF if we are using JWT
                .authorizeExchange(authorize -> authorize
                        .pathMatchers(allowedRoutes).permitAll() // Public routes
                        .pathMatchers("/byeAdmin").hasRole("ADMIN") // Route for users with role ADMIN
                        .pathMatchers("/bye").hasRole("USER") // Route for users with role USER
                        .anyExchange().authenticated() // Any other route requires authentication
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(authConverter))
                );

        return http.build();
    }
}
