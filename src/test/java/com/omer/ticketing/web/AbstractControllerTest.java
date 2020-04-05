package com.omer.ticketing.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    protected void setup(Object... controllers) {
        Objects.requireNonNull(controllers, "Controllers cant not be null");
        mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
        objectMapper = new ObjectMapper();
    }
}
