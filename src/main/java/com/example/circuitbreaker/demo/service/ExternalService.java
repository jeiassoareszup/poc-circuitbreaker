package com.example.circuitbreaker.demo.service;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import com.example.circuitbreaker.demo.commons.exception.ExternalServiceCallException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public abstract class ExternalService {

    public ExternalService(CircuitBreaker circuitBreaker, TimeLimiter timeLimiter) {
        this.timeLimiter = timeLimiter;
        this.circuitBreaker = circuitBreaker;
    }

    private CircuitBreaker circuitBreaker;
    private TimeLimiter timeLimiter;

    public abstract SampleDTO confirm();

    public abstract SampleDTO timeout();


    //TODO: Verificar necessidade de metodo generico
//    <T> T callSupplier(Callable<T> callable) {
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Supplier<Future<T>> futureSupplier = () -> executorService.submit(callable);
//
//        Callable<T> supplier = TimeLimiter.decorateFutureSupplier(timeLimiter, futureSupplier);
//        Callable<T> chainedCallable = CircuitBreaker.decorateCallable(circuitBreaker, supplier);
//
//        Try<T> tried = Try.of(chainedCallable::call)
//                .onFailure(throwable -> {
//                    throw new ExternalServiceCallException("External call unreachable");
//                });
//
//        return tried.get();
//    }

    SampleDTO callSupplier(Callable<SampleDTO> callable) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Supplier<Future<SampleDTO>> futureSupplier = () -> executorService.submit(callable);

        Callable<SampleDTO> supplier = TimeLimiter.decorateFutureSupplier(timeLimiter, futureSupplier);
        Callable<SampleDTO> chainedCallable = CircuitBreaker.decorateCallable(circuitBreaker, supplier);

        long start = System.nanoTime();

        Try<SampleDTO> tried = Try.of(chainedCallable::call)
                .onSuccess(v -> v.setRequestTime(Duration.ofNanos(System.nanoTime() - start).toMillis()))
                .onFailure(throwable -> {
                    throw new ExternalServiceCallException("External call unreachable", System.nanoTime() - start);
                });

        return tried.get();
    }
}
