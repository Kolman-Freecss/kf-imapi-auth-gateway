package org.kolmanfreecss.kfimapiauthgateway.infrastructure.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * This class is responsible for handling the authentication entry point.
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
@Slf4j
@Component
public class KfAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String responseBody = "{\"error\": \"Unauthorized access\", \"message\": \"" + authException.getMessage() + "\"}";

        return Mono.fromRunnable(() -> {
            try {
                exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(responseBody.getBytes())));
            } catch (Exception e) {
                log.error("Error writing response", e);
            }
        }).then();
    }
}
