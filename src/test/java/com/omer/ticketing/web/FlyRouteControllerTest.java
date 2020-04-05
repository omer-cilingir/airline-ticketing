package com.omer.ticketing.web;

import com.omer.ticketing.model.FlyRouteDto;
import com.omer.ticketing.model.request.FlyRouteCreateRequest;
import com.omer.ticketing.service.FlyRouteService;
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

class FlyRouteControllerTest extends AbstractControllerTest {

    @Mock
    FlyRouteService flyRouteService;

    @BeforeEach
    void setUp() {
        super.setup(new FlyRouteController(flyRouteService));
    }

    @Test
    void createFlyRoute() throws Exception {
        FlyRouteCreateRequest request = FlyRouteCreateRequest.builder()
                .toAirportId(1L)
                .fromAirportId(2L)
                .build();

        doNothing().when(flyRouteService).createFlyRoute(request);
        mockMvc.perform(post("/fly-route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(flyRouteService).createFlyRoute(request);
    }

    @Test
    void getAllFlyRoute() throws Exception {

        final List<FlyRouteDto> expectedResponse = Collections.singletonList(FlyRouteDto.builder()
                .fromAirportName("TRABZON")
                .build());

        when(flyRouteService.getAllFlyRoute()).thenReturn(expectedResponse);

        mockMvc.perform(get("/fly-route")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].fromAirportName", is("TRABZON")))
                .andExpect(status().is2xxSuccessful());

        verify(flyRouteService).getAllFlyRoute();
    }

    @Test
    void searchByFromName() throws Exception {
        final List<FlyRouteDto> expectedResponse = Collections.singletonList(FlyRouteDto.builder()
                .fromAirportName("TRABZON")
                .build());

        when(flyRouteService.searchByFromName("T")).thenReturn(expectedResponse);

        mockMvc.perform(get("/fly-route/search/from")
                .contentType(MediaType.APPLICATION_JSON).param("keyword", "T"))
                .andExpect(jsonPath("$.[0].fromAirportName", is("TRABZON")))
                .andExpect(status().is2xxSuccessful());

        verify(flyRouteService).searchByFromName("T");
    }
}