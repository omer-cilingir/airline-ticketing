package com.omer.ticketing.web;

import com.omer.ticketing.model.FlightDto;
import com.omer.ticketing.model.request.FlightCreateRequest;
import com.omer.ticketing.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlightControllerTest extends AbstractControllerTest {

    @Mock
    FlightService flightService;

    @BeforeEach
    void setUp() {
        super.setup(new FlightController(flightService));
    }

    @Test
    void createFlight() throws Exception {
        FlightCreateRequest request = FlightCreateRequest.builder()
                .airlineBusinessId(1L)
                .ticketCount(20L)
                .flightDuration("10m")
                .build();

        doNothing().when(flightService).createFlight(request);
        mockMvc.perform(post("/flight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(flightService).createFlight(request);
    }

    @Test
    void getAllFlight() throws Exception {

        final List<FlightDto> expectedResponse = Collections.singletonList(FlightDto.builder()
                .airlineBusinessName("THY")
                .build());
        when(flightService.getAllFlight()).thenReturn(expectedResponse);
        mockMvc.perform(get("/flight")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].airlineBusinessName", is("THY")))
                .andExpect(status().is2xxSuccessful());

        verify(flightService).getAllFlight();
    }

    @Test
    void searchByAirlineBusinessName() throws Exception {

        final List<FlightDto> expectedResponse = Collections.singletonList(FlightDto.builder()
                .airlineBusinessName("THY")
                .build());
        when(flightService.searchByAirlineBusinessName("T")).thenReturn(expectedResponse);
        mockMvc.perform(get("/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "T"))
                .andExpect(jsonPath("$.[0].airlineBusinessName", is("THY")))
                .andExpect(status().is2xxSuccessful());

        verify(flightService).searchByAirlineBusinessName("T");
    }
}