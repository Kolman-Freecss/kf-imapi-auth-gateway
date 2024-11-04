package org.kolmanfreecss.kfimapiauthgateway.infrastructure.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Redis configuration class for Cache implementation and Rate limiting.
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
@Configuration
public class RedisConfig {

//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(java.time.Duration.ofSeconds(60))
//                .disableCachingNullValues()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }

    /**
     * Rate limiting Config -> Key resolver
     *
     * @return KeyResolver
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            // JMeter performance testing
            final String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
            // Get the IP address of the client
            return Mono.just(userId != null ? userId : Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString());
        };
    }

    /**
     * Rate limiting Config -> RedisRateLimiter
     * 10 requests per second with a burst capacity of 20
     *
     * @return RedisRateLimiter
     */
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(3, 5);
    }


}
