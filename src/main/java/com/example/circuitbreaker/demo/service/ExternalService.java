package com.example.circuitbreaker.demo.service;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.control.Try;

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

    <T> T callSupplier(Callable<T> callable) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Supplier<Future<T>> futureSupplier = () -> executorService.submit(callable);

        Callable<T> supplier = TimeLimiter.decorateFutureSupplier(timeLimiter, futureSupplier);
        Callable<T> chainedCallable = CircuitBreaker.decorateCallable(circuitBreaker, supplier);

        Try<T> tried = Try.of(chainedCallable::call)
                .onFailure(throwable -> new RuntimeException("service call failed."));

        return tried.get();
    }
}
