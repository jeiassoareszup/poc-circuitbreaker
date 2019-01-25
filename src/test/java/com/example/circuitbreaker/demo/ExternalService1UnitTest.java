package com.example.circuitbreaker.demo;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import com.example.circuitbreaker.demo.feign.ExternalService1Connector;
import com.example.circuitbreaker.demo.service.ExternalService1;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ExternalService1UnitTest {

    private ExternalService1 externalService1;
    private CircuitBreaker circuitBreaker;
    private ExternalService1Connector service1Connector = Mockito.mock(ExternalService1Connector.class);

    private static final int FAILURE_RATE_THRESHOLD = 50;
    private static final int BUFFER_SIZE_IN_CLOSED_STATE = 4;


    private CircuitBreaker getCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(FAILURE_RATE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .ringBufferSizeInClosedState(BUFFER_SIZE_IN_CLOSED_STATE)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

        return registry.circuitBreaker("service1CircuitBreaker");
    }

    private TimeLimiter getTimeLimiter() {

        TimeLimiterConfig limiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5))
                .build();

        return TimeLimiter.of(limiterConfig);
    }

    @Before
    public void before() {
        this.circuitBreaker = getCircuitBreaker();
        this.externalService1 = new ExternalService1(service1Connector, circuitBreaker, getTimeLimiter());
    }

    @Test
    public void closedCircuit() {
        circuitBreaker.reset();

        when(service1Connector.getConfirmation()).thenReturn(new SampleDTO("success"));

        for (int i = 0; i < BUFFER_SIZE_IN_CLOSED_STATE; i++) {
            externalService1.confirm();
        }

        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
        assertEquals(BUFFER_SIZE_IN_CLOSED_STATE, circuitBreaker.getMetrics().getNumberOfSuccessfulCalls());

    }

    @Test
    public void openCircuit() {
        circuitBreaker.reset();

        when(service1Connector.getTimeout()).thenThrow(new RuntimeException());

        for (int i = 0; i < BUFFER_SIZE_IN_CLOSED_STATE; i++) {
            try {
                externalService1.timeout();
            } catch (Throwable ignored) {
            }
        }

        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
        assertEquals(BUFFER_SIZE_IN_CLOSED_STATE, circuitBreaker.getMetrics().getNumberOfBufferedCalls());

    }

    @Test
    public void callMockOpenCircuit() {
        circuitBreaker.reset();
        int callMock = 3;

        when(service1Connector.getTimeout()).thenThrow(new RuntimeException());

        for (int i = 0; i < BUFFER_SIZE_IN_CLOSED_STATE + callMock; i++) {
            try {
                externalService1.timeout();
            } catch (Throwable ignored) {
            }
        }

        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
        assertEquals(BUFFER_SIZE_IN_CLOSED_STATE, circuitBreaker.getMetrics().getNumberOfBufferedCalls());
        verify(service1Connector, times(BUFFER_SIZE_IN_CLOSED_STATE)).getTimeout();
    }

    @Test
    public void aboveThreshold() {
        circuitBreaker.reset();

        int errorCalls = ((FAILURE_RATE_THRESHOLD * BUFFER_SIZE_IN_CLOSED_STATE) / 100) + 1;

        when(service1Connector.getTimeout()).thenThrow(new RuntimeException());
        when(service1Connector.getConfirmation()).thenReturn(new SampleDTO("success"));

        for (int i = 0; i < errorCalls; i++) {
            try {
                externalService1.timeout();
            } catch (Throwable ignored) {
            }
        }

        for (int i = 0; i < BUFFER_SIZE_IN_CLOSED_STATE - errorCalls ; i++) {
            externalService1.confirm();
        }

        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
        assertEquals(errorCalls, circuitBreaker.getMetrics().getNumberOfFailedCalls());
        verify(service1Connector, times(errorCalls)).getTimeout();
        verify(service1Connector, times(BUFFER_SIZE_IN_CLOSED_STATE - errorCalls)).getConfirmation();
    }

    @Test
    public void belowThreshold() {
        circuitBreaker.reset();

        int successCalls = ((FAILURE_RATE_THRESHOLD * BUFFER_SIZE_IN_CLOSED_STATE) / 100) + 1;

        when(service1Connector.getTimeout()).thenThrow(new RuntimeException());
        when(service1Connector.getConfirmation()).thenReturn(new SampleDTO("success"));


        for (int i = 0; i < successCalls ; i++) {
            externalService1.confirm();
        }

        for (int i = 0; i < BUFFER_SIZE_IN_CLOSED_STATE - successCalls; i++) {
            try {
                externalService1.timeout();
            } catch (Throwable ignored) {
            }
        }

        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
        assertEquals(successCalls, circuitBreaker.getMetrics().getNumberOfSuccessfulCalls());
        verify(service1Connector, times(successCalls)).getConfirmation();
        verify(service1Connector, times(BUFFER_SIZE_IN_CLOSED_STATE - successCalls)).getTimeout();
    }

}
