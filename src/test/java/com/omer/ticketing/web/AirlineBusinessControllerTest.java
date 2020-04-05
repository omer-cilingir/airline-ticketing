package com.omer.ticketing.web;

import com.omer.ticketing.model.AirlineBusinessDto;
import com.omer.ticketing.service.AirlineBusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AirlineBusinessControllerTest extends AbstractControllerTest {

    @Mock
    private AirlineBusinessService airlineBusinessService;

    @BeforeEach
    public void setup() {
        super.setup(new AirlineBusinessController(airlineBusinessService));
    }

    @Test
    void createAirlineBusiness() throws Exception {
        final AirlineBusinessDto request = AirlineBusinessDto.builder().name("THY").build();

        doNothing().when(airlineBusinessService).createAirlineBusiness(any());

        mockMvc.perform(post("/airline-business")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(airlineBusinessService).createAirlineBusiness(request);
    }

    @Test
    void getAllAirlineBusiness() throws Exception {
        List<AirlineBusinessDto> expectedResponse = Collections.singletonList(AirlineBusinessDto.builder()
                .name("THY")
                .id(1L)
                .createdDate(LocalDateTime.of(2020, 1, 1, 1, 1))
                .build());

        when(airlineBusinessService.getAllAirlineBusiness()).thenReturn(expectedResponse);

        mockMvc.perform(get("/airline-business")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].name", is("THY")))
                .andExpect(status().is2xxSuccessful());
        verify(airlineBusinessService).getAllAirlineBusiness();

    }

    @Test
    void searchAirlineBusiness() throws Exception {
        List<AirlineBusinessDto> expectedResponse = Collections.singletonList(AirlineBusinessDto.builder()
                .name("THY")
                .id(1L)
                .createdDate(LocalDateTime.of(2020, 1, 1, 1, 1))
                .build());

        when(airlineBusinessService.searchAirlineBusiness("T")).thenReturn(expectedResponse);

        mockMvc.perform(get("/airline-business/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "T"))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].name", is("THY")))
                .andExpect(status().is2xxSuccessful());

        verify(airlineBusinessService).searchAirlineBusiness("T");
    }
}