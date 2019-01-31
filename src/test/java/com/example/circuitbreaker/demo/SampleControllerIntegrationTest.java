package com.example.circuitbreaker.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class SampleControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Value("${service1.time-limiter.timeout}")
    private Integer service1Timeout;

    @Value("${service2.time-limiter.timeout}")
    private Integer service2Timeout;

    private static final Integer TIMEOUT_TOLERANCE_IN_SECONDS = 1;

    @Test
    public void service1Confirmation() throws Exception {
        this.mvc.perform(get("/service1/confirmation")).andExpect(status().isOk())
                .andExpect(content().json("{\"msg\": \"service 1: message received!\"}"));
    }

    @Test
    public void service2Confirmation() throws Exception {
        this.mvc.perform(get("/service2/confirmation")).andExpect(status().isOk())
                .andExpect(content().json("{\"msg\": \"service 2: message received!\"}"));
    }

    @Test
    public void service2Timeout() throws Exception {

        this.mvc.perform(get("/service2/timeout")).andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.requestTime", lessThan((service2Timeout + TIMEOUT_TOLERANCE_IN_SECONDS) * 1000)));
    }

    @Test
    public void service1Timeout() throws Exception {

        this.mvc.perform(get("/service1/timeout")).andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.requestTime", lessThan((service1Timeout + TIMEOUT_TOLERANCE_IN_SECONDS) * 1000)));

    }
}
