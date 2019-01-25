package com.example.circuitbreaker.demo.service;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InternalService {

    private ExternalService service1;

    private ExternalService service2;

    @Autowired
    public InternalService(@Qualifier("externalService1") final ExternalService service1, @Qualifier("externalService2") final ExternalService service2) {
        this.service1 = service1;
        this.service2 = service2;
    }

    public SampleDTO getService1Confirmation() {
        return service1.confirm();
    }

    public SampleDTO getService2Timeout() {
        return service2.timeout();
    }

    public SampleDTO getService2Confirmation() {
        return service2.confirm();
    }

    public SampleDTO getService1Timeout() {
        return service1.timeout();
    }
}
