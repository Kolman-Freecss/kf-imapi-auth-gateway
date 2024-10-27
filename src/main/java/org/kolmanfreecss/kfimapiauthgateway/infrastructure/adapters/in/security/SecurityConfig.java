package org.kolmanfreecss.kfimapiauthgateway.infrastructure.adapters.in.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    final JwtAuthConverter authConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF if we are using JWT
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/hello").permitAll() // Public route
                        .requestMatchers("/byeAdmin").hasRole("ADMIN") // Route for users with role ADMIN
                        .requestMatchers("/bye").hasRole("USER") // Route for users with role USER
                        .anyRequest().authenticated() // Any other route requires authentication
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(authConverter))
                );

        return http.build();
    }
}
