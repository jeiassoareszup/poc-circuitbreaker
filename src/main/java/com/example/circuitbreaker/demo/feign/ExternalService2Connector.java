package com.example.circuitbreaker.demo.feign;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="externalService2Connector", url = "http://localhost:9009/external-service2")
public interface ExternalService2Connector {

    @RequestMapping("/timeout")
    SampleDTO getTimeout();
}
