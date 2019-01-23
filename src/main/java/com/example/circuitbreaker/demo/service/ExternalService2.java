package com.example.circuitbreaker.demo.service;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import com.example.circuitbreaker.demo.feign.ExternalService2Connector;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("externalService2")
public class ExternalService2 extends ExternalService {

    private ExternalService2Connector service2Connector;

    public ExternalService2(final ExternalService2Connector service2Connector, @Qualifier("service2CircuitBreaker") final CircuitBreaker circuitBreaker, @Qualifier("service2TimeLimiter") final TimeLimiter timeLimiter) {
        super(circuitBreaker, timeLimiter);
        this.service2Connector = service2Connector;
    }

    @Override
    public SampleDTO confirm() {
        return new SampleDTO("Service2 received message: ");
    }

    @Override
    public SampleDTO timeout() {
        return super.callSupplier(service2Connector::getTimeout);
    }
}
