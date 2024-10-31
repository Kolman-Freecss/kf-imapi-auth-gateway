package org.kolmanfreecss.kfimapiauthgateway.infrastructure.adapters.in.gateway;

import org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest.FallbackController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

import java.util.function.Function;

/**
 * GatewayConfig
 * Used to configure the gateway.
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
@Configuration
public class GatewayConfig {

    private static final String INTERNAL_AUTH_HEADER = "X-Internal-Auth";

    private static final String FALLBACK_URI = "forward:/fallback";

    @Value("${gateway.internal-auth-secret}")
    private String internalAuthSecret;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Service route for "responses"
                .route("users_service", route -> route
                        .path("/api/v1/responses/**")
                        .filters(customFilters("responses"))
                        .uri("lb://KF-IMAPI-RESPONSES-SERVICE")) // Using Eureka service name

                // Service route for "incident"
                .route("incident_service", route -> route
                        .path("/api/v1/incident/**")
                        .filters(customFilters("incident"))
                        .uri("lb://KF-IMAPI-INCIDENT-SERVICE"))

                // Service route for "notifications"
                .route("notifications_service", route -> route
                        .path("/api/v1/notifications/**")
                        .filters(customFilters("notifications"))
                        .uri("lb://KF-IMAPI-NOTIFICATIONS-SERVICE"))

                .build();
    }

    /**
     * Custom filters for the gateway.
     *
     * @param serviceName The name of the service.
     * @return The custom filters.
     */
    private Function<GatewayFilterSpec, UriSpec> customFilters(final String serviceName) {
        return f -> f.filter((exchange, chain) -> {
            // We need to override the headers to add the internal auth secret because they are immutable by default 
            final ServerHttpRequest decoratedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    final HttpHeaders headers = new HttpHeaders();
                    headers.putAll(super.getHeaders());
                    headers.add(INTERNAL_AUTH_HEADER, internalAuthSecret); // Add new header
                    return headers;
                }
            };

            // Continue the filter chain with the decorated request
            return chain.filter(exchange.mutate().request(decoratedRequest).build());
        }).circuitBreaker(c -> c.setName(serviceName + "CircuitBreaker")
                .setFallbackUri(FALLBACK_URI));
    }

    @Bean
    public FallbackController fallbackController() {
        return new FallbackController();
    }

}
