package com.example.circuitbreaker.demo.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RefreshScope
public class CircuitBreakerConfiguration {

    @Bean
    @RefreshScope
    @Qualifier("service1CircuitBreaker")
    public CircuitBreaker service1CircuitBreaker(@Value("${service1.circuit-breaker.threshold}") Integer threshold, @Value("${service1.circuit-breaker.wait.time}") Integer waitTime, @Value("${service1.circuit-breaker.closed.state.buffer}") Integer closedStateBuffer) {

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(threshold)
                .waitDurationInOpenState(Duration.ofMillis(waitTime))
                .ringBufferSizeInClosedState(closedStateBuffer)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

        return registry.circuitBreaker("service1CircuitBreaker");
    }

    @Bean
    @RefreshScope
    @Qualifier("service2CircuitBreaker")
    public CircuitBreaker service2CircuitBreaker(@Value("${service2.circuit-breaker.threshold}") Integer threshold, @Value("${service2.circuit-breaker.wait.time}") Integer waitTime, @Value("${service2.circuit-breaker.closed.state.buffer}") Integer closedStateBuffer) {

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(threshold)
                .waitDurationInOpenState(Duration.ofMillis(waitTime))
                .ringBufferSizeInClosedState(closedStateBuffer)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

        return registry.circuitBreaker("service2CircuitBreaker");
    }
}
