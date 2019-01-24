package com.example.circuitbreaker.demo.handler;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import com.example.circuitbreaker.demo.commons.example.ExternalServiceCallException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Duration;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ExternalServiceCallException.class)
    public ResponseEntity<SampleDTO> handleNotFound(ExternalServiceCallException ex) {
        return new ResponseEntity<>(new SampleDTO(ex.getMessage(), Duration.ofNanos(ex.getTime()).toMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
