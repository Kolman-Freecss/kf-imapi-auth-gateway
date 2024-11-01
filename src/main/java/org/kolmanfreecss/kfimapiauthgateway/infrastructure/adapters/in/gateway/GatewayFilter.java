package org.kolmanfreecss.kfimapiauthgateway.infrastructure.adapters.in.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;

import static org.kolmanfreecss.kfimapiauthgateway.infrastructure.adapters.in.gateway.GatewayConfig.INTERNAL_AUTH_HEADER;

/**
 * GatewayFilter
 * Used to filter the gateway.
 * We need to use this filter to modify the immutable request headers because not in every phase of the request lifecycle we can modify the headers.
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
@Component
public class GatewayFilter extends AbstractGatewayFilterFactory<GatewayFilter.Config> {

    @Value("${gateway.internal-auth-secret}")
    private String internalAuthSecret;

    @Override
    public org.springframework.cloud.gateway.filter.GatewayFilter apply(GatewayFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpRequestDecorator decoratedRequest = getDecoratedRequest(request);
            return chain.filter(exchange.mutate().request(decoratedRequest).build());
        };
    }

    private ServerHttpRequestDecorator getDecoratedRequest(ServerHttpRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(request.getHeaders());
        if (!headers.containsKey(INTERNAL_AUTH_HEADER)) {
            headers.add(INTERNAL_AUTH_HEADER, internalAuthSecret);
        }

        return new ServerHttpRequestDecorator(request) {
            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
        };
    }

    public static class Config {
        // Configuration properties
    }
}
