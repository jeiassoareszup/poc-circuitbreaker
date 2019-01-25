package com.example.circuitbreaker.demo.service;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import com.example.circuitbreaker.demo.feign.ExternalService1Connector;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("externalService1")
public class ExternalService1 extends ExternalService {

    private ExternalService1Connector service1Connector;

    @Autowired
    public ExternalService1(final ExternalService1Connector service1Connector, @Qualifier("service1CircuitBreaker") final CircuitBreaker circuitBreaker, @Qualifier("service1TimeLimiter") final TimeLimiter timeLimiter) {
        super(circuitBreaker, timeLimiter);
        this.service1Connector = service1Connector;
    }

    @Override
    public SampleDTO confirm() {
        return super.callSupplier(service1Connector::getConfirmation);
    }

    @Override
    public SampleDTO timeout() {
        return super.callSupplier(service1Connector::getTimeout);
    }
}
