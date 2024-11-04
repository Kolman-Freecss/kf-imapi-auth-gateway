//package org.kolmanfreecss.kfimapiauthgateway.infrastructure.config;
//
//import lombok.NonNull;
//import lombok.extern.log4j.Log4j2;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.UncheckedIOException;
//import java.nio.charset.StandardCharsets;
//
//@Log4j2
//@Component
//public class CachingGatewayFilter {
//
//    private final ReactiveRedisOperations<String, String> redisOperations;
//
//    public CachingGatewayFilter(ReactiveRedisOperations<String, String> redisOperations) {
//        this.redisOperations = redisOperations;
//    }
//
//    /**
//     * Gateway filter to cache responses from downstream services.
//     * Only GET requests are cached.
//     *
//     * @return GatewayFilter
//     */
//    public GatewayFilter cacheResponse() {
//        return (exchange, chain) -> {
//            final HttpMethod requestMethod = exchange.getRequest().getMethod();
//
//            // Only cache GET requests
//            if (!requestMethod.equals(HttpMethod.GET)) {
//                return chain.filter(exchange);
//            }
//
//            // Generate a cache key based on the request
//            final String cacheKey = generateCacheKey(exchange.getRequest());
//
//            // Check the cache first
//            return redisOperations.opsForValue().get(cacheKey)
//                    .flatMap(response -> {
//                        // Write the cached response to the client
//                        ServerHttpResponse serverHttpResponse = exchange.getResponse();
//                        serverHttpResponse.setStatusCode(HttpStatus.OK);
//                        serverHttpResponse.getHeaders().add("Content-Type", "application/json");
//                        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
//                        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(responseBytes);
//                        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
//                    })
//                    .switchIfEmpty(chain.filter(exchange)
//                            .doOnError(throwable -> log.error("Error in chain processing request: {}", throwable.getMessage()))
//                            .then(Mono.defer(() -> cacheOriginalResponse(exchange, chain, cacheKey)))
//                            .then(redisOperations.opsForValue().get(cacheKey)).then());
//
//        };
//    }
//
//    private Mono<Void> cacheOriginalResponse(ServerWebExchange exchange, GatewayFilterChain chain, String cacheKey) {
//        // Create a ByteArrayOutputStream to capture the response body
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        // Decorate the response to intercept the writeWith call
//        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {
//            @Override
//            @NonNull
//            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
//                System.out.println("222222222");
//                // Capture the response body
//                return super.writeWith(Flux.from(body).doOnNext(dataBuffer -> {
//                    // Capture the response body
//                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                    dataBuffer.read(bytes);
//
////                    DataBufferUtils.release(dataBuffer);
//
//                    try {
//                        outputStream.write(bytes);
//                    } catch (IOException e) {
//                        log.error("Error writing to output stream: {}", e.getMessage());
//                        throw new UncheckedIOException(e);
//                    }
//                }));
//            }
//
//        };
//
//        System.out.println("111111111");
//        // Check if the response has already been committed
//        if (exchange.getResponse().isCommitted()) {
//            System.out.println("Response already committed");
//            return Mono.empty();
//        }
//
//        // Proceed with the filter chain and capture the response
//        return chain.filter(exchange.mutate().response(responseDecorator).build())
//                .then(Mono.defer(() -> {
//                    System.out.println("Caching response for key: " + cacheKey);
//                    // After the response has been written, get the captured response body
//                    String originalResponseBody = outputStream.toString(StandardCharsets.UTF_8); // Convert captured data to string
//                    System.out.println("Original response body: " + originalResponseBody);
//                    // Store the response in cache
//                    return redisOperations.opsForValue().set(cacheKey, originalResponseBody)
//                            .then(Mono.empty()); // Complete the Mono
//                }));
//    }
//
//
//    private String generateCacheKey(ServerHttpRequest request) {
//        // Build a unique cache key based on HTTP method, request path, and query parameters
//        StringBuilder keyBuilder = new StringBuilder();
//
//        // Append the HTTP method (GET, POST, etc.)
//        keyBuilder.append(request.getMethod()).append(":");
//
//        // Append the request path
//        keyBuilder.append(request.getURI().getPath()).append("?");
//
//        // Append query parameters
//        request.getQueryParams().forEach((key, value) -> {
//            keyBuilder.append(key).append("=").append(String.join(",", value)).append("&");
//        });
//
//        // Remove the last '&' if it exists
//        if (keyBuilder.charAt(keyBuilder.length() - 1) == '&') {
//            keyBuilder.deleteCharAt(keyBuilder.length() - 1);
//        }
//
//        return keyBuilder.toString();
//    }
//}
