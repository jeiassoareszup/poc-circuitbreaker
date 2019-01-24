package com.example.circuitbreaker.demo.handler;

import com.example.circuitbreaker.demo.commons.dto.ExternalErrorDTO;
import com.example.circuitbreaker.demo.commons.example.ExternalServiceCallException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ExternalServiceCallException.class)
    public ResponseEntity<ExternalErrorDTO> handleNotFound(ExternalServiceCallException ex) {
        return new ResponseEntity<>(new ExternalErrorDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
