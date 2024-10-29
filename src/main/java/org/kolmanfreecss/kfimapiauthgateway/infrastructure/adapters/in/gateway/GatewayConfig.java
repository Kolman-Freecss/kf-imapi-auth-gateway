package org.kolmanfreecss.kfimapiauthgateway.infrastructure.adapters.in.gateway;

import org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest.FallbackController;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GatewayConfig
 * Used to configure the gateway.
 *
 * @version 1.0
 * @uthor Kolman-Freecss
 */
@Configuration
public class GatewayConfig {
    
    private static final String FALLBACK_URI = "forward:/fallback";

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Service route for "incident"
                .route("users_service", route -> route
                        .path("/users/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("usersCircuitBreaker")
                                        .setFallbackUri(FALLBACK_URI)))
                        .uri("lb://USERS-SERVICE")) // Using Eureka service name

                // Service route for "incident"
                .route("incident_service", route -> route
                        .path("/incident/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("incidentCircuitBreaker")
                                        .setFallbackUri(FALLBACK_URI)))
                        .uri("lb://INCIDENT-SERVICE"))

                // Service route for "auth"
                .route("auth_service", route -> route
                        .path("/auth/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("authCircuitBreaker")
                                        .setFallbackUri(FALLBACK_URI)))
                        .uri("lb://AUTH-SERVICE"))

                .build();
    }

    @Bean
    public FallbackController fallbackController() {
        return new FallbackController();
    }

}
