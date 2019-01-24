package com.example.circuitbreaker.demo.commons.example;

public class ExternalServiceCallException extends RuntimeException {

    public ExternalServiceCallException(String message) {
        super(message);
    }

}
