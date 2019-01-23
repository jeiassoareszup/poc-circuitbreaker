package com.example.circuitbreaker.demo.config;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class TimeLimiterConfiguration {

    @Bean
    @Qualifier("service1TimeLimiter")
    public TimeLimiter service1TimeLimiter(@Value("${service1.time-limiter.timeout}") Integer timeout) {

        TimeLimiterConfig limiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(timeout))
                .build();

        return TimeLimiter.of(limiterConfig);
    }

    @Bean
    @Qualifier("service2TimeLimiter")
    public TimeLimiter service2TimeLimiter(@Value("${service2.time-limiter.timeout}") Integer timeout) {

        TimeLimiterConfig limiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(timeout))
                .build();

        return TimeLimiter.of(limiterConfig);
    }

}
