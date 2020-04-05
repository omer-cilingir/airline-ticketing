package com.omer.ticketing.web;

import com.omer.ticketing.model.AirportDto;
import com.omer.ticketing.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AirportControllerTest extends AbstractControllerTest {

    @Mock
    private AirportService airportService;

    @BeforeEach
    void setUp() {
        super.setup(new AirportController(airportService));
    }

    @Test
    void createAirport() throws Exception {
        final AirportDto request = AirportDto.builder().name("TRABZON")
                .code("TZX")
                .build();

        doNothing().when(airportService).createAirport(any());

        mockMvc.perform(post("/airport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(airportService).createAirport(request);
    }

    @Test
    void searchAirport() throws Exception {
        List<AirportDto> expectedResponse = Collections.singletonList(AirportDto.builder().name("TRABZON")
                .code("TZX")
                .id(1L)
                .build());
        when(airportService.searchAirport("T")).thenReturn(expectedResponse);

        mockMvc.perform(get("/airport/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "T"))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].code", is("TZX")))
                .andExpect(jsonPath("$.[0].name", is("TRABZON")))
                .andExpect(status().is2xxSuccessful());

        verify(airportService).searchAirport("T");
    }

    @Test
    void findAll() throws Exception {

        List<AirportDto> expectedResponse = Collections.singletonList(AirportDto.builder().name("TRABZON")
                .code("TZX")
                .id(1L)
                .build());
        when(airportService.getAllAirport()).thenReturn(expectedResponse);

        mockMvc.perform(get("/airport")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].code", is("TZX")))
                .andExpect(jsonPath("$.[0].name", is("TRABZON")))
                .andExpect(status().is2xxSuccessful());
        verify(airportService).getAllAirport();
    }
}