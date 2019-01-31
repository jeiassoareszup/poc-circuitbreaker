package com.example.circuitbreaker.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.cloud.endpoint.event.RefreshEventListener;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PocCircuitBreakerApplication {

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(PocCircuitBreakerApplication.class, args);
    }

    @EventListener
    public void appListenerExecute(ApplicationReadyEvent event){
        RefreshEventListener bean = context.getBean(RefreshEventListener.class);
        bean.handle(event);
    }

    @EventListener
    public void RefreshEventListenerExecute(RefreshEvent event){
        RefreshEventListener bean = context.getBean(RefreshEventListener.class);
        bean.handle(event);
    }

}

