package com.example.circuitbreaker.demo.commons.exception;

public class ExternalServiceCallException extends RuntimeException {

    private final long time;

    public ExternalServiceCallException(String message, long time) {
        super(message);
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
