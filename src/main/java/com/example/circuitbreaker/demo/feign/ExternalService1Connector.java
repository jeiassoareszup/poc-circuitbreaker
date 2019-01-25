package com.example.circuitbreaker.demo.feign;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="externalService1Connector", url = "http://localhost:9009/external-service1")
public interface ExternalService1Connector {

    @RequestMapping("/confirmation")
    SampleDTO getConfirmation();

    @RequestMapping("/timeout")
    SampleDTO getTimeout();
}
