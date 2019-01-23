package com.example.circuitbreaker.demo.feign;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="externalService2Connector", url = "10.255.255.1")
public interface ExternalService2Connector {

    @RequestMapping("/")
    SampleDTO getTimeout();
}
