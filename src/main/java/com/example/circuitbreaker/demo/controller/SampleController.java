package com.example.circuitbreaker.demo.controller;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import com.example.circuitbreaker.demo.service.InternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private InternalService internalService;

    @Autowired
    public SampleController(final InternalService internalService) {
        this.internalService = internalService;
    }

    @GetMapping("/service1/confirmation")
    public SampleDTO getService1Message() {
        return internalService.getService1Confirmation();
    }

    @GetMapping("/service2/timeout")
    public SampleDTO getService2Timeout() {
        return internalService.getService2Timeout();
    }

    @GetMapping("/service2/confirmation")
    public SampleDTO getService2Message() {
        return internalService.getService2Confirmation();
    }
}
