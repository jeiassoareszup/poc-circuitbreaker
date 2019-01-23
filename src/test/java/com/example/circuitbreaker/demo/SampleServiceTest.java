//package com.example.circuitbreaker.demo;
//
//import com.example.circuitbreaker.demo.service.ExternalService;
//import com.example.circuitbreaker.demo.service.ExternalService1;
//import com.example.circuitbreaker.demo.service.ExternalService2;
//import com.example.circuitbreaker.demo.service.InternalService;
//import io.github.resilience4j.circuitbreaker.CircuitBreaker;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.time.Duration;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class SampleServiceTest {
//
//    private ExternalService externalService1 = Mockito.mock(ExternalService1.class);
//    private ExternalService externalService2 = Mockito.mock(ExternalService2.class);
//
//    private InternalService service;
//
//    private CircuitBreaker getService1CircuitBreaker() {
//        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .waitDurationInOpenState(Duration.ofMillis(9999))
//                .ringBufferSizeInClosedState(3)
//                .build();
//
//        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
//
//        return registry.circuitBreaker("service1CircuitBreaker");
//    }
//
//    private CircuitBreaker getService2CircuitBreaker() {
//        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .waitDurationInOpenState(Duration.ofMillis(9999))
//                .ringBufferSizeInClosedState(2)
//                .build();
//
//        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
//
//        return registry.circuitBreaker("service1CircuitBreaker");
//    }
//
//    @Before
//    public void before() {
//
//        CircuitBreaker circuitBreaker1 = getService1CircuitBreaker();
//        CircuitBreaker circuitBreaker2 = getService2CircuitBreaker();
//
//        this.service = new InternalService(this.externalService1, this.externalService2, circuitBreaker1, circuitBreaker2);
//    }
//
//    @Test
//    public void service1Called5Times() {
//        when(externalService1.confirm(anyString())).thenThrow(new RuntimeException());
//
//        for (int i = 0; i < 10; i++) {
//            try {
//                service.getService1Confirmation("test");
//            } catch (Exception ignore) {
//            }
//        }
//
//        verify(externalService1, times(3)).confirm(anyString());
//    }
//
//    @Test
//    public void service2Called5Times() {
//        when(externalService2.confirm(anyString())).thenThrow(new RuntimeException());
//
//        for (int i = 0; i < 10; i++) {
//            try {
//                service.getService2Confirmation("test");
//            } catch (Exception ignore) {
//            }
//        }
//
//        verify(externalService2, times(2)).confirm(anyString());
//    }
//}
