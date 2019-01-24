package com.example.circuitbreaker.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class SampleControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void service1Confirmation() throws Exception {
        this.mvc.perform(get("/service1/confirmation")).andExpect(status().isOk())
                .andExpect(content().json("{\"msg\": \"message received!\"}"));
    }

    @Test
    public void service2Timeout() throws Exception {

        for (int i = 0; i < 7; i++) {
            this.mvc.perform(get("/service2/timeout")).andExpect(status().is5xxServerError());
        }

    }
}
