package com.example.circuitbreaker.demo.feign;

import com.example.circuitbreaker.demo.commons.dto.SampleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="externalService1Connector", url = "http://www.mocky.io")
public interface ExternalService1Connector {

    @RequestMapping("/v2/5c477ace3100002b008a1e08")
    SampleDTO getConfirmation();
}
