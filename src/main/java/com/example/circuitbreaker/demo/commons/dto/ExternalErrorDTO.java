package com.example.circuitbreaker.demo.commons.dto;

public class ExternalErrorDTO {

    public ExternalErrorDTO(String cause) {
        this.cause = cause;
    }

    private String cause;

    public String getCause() {
        return cause;
    }
}
