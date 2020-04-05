package com.omer.ticketing.web;

import com.omer.ticketing.model.BookFlightDto;
import com.omer.ticketing.model.request.BookFlightRequest;
import com.omer.ticketing.service.BookFlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookFlightControllerTest extends AbstractControllerTest {
    @Mock
    BookFlightService bookFlightService;

    @BeforeEach
    void setUp() {
        super.setup(new BookFlightController(bookFlightService));
    }

    @Test
    void bookFlight() throws Exception {
        BookFlightRequest request = BookFlightRequest.builder().flightId(1L).build();
        BookFlightDto expectedResponse = BookFlightDto.builder()
                .flightName("TEST FLY")
                .build();
        when(bookFlightService.bookFlight(request)).thenReturn(expectedResponse);
        mockMvc.perform(post("/book-flight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.flightName", is("TEST FLY")))
                .andExpect(status().is2xxSuccessful());
        verify(bookFlightService).bookFlight(request);
    }

    @Test
    void cancelBookFlight() throws Exception {
        doNothing().when(bookFlightService).cancelFlight(1L);

        mockMvc.perform(delete("/book-flight")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookFlightId", "1"))
                .andExpect(status().isAccepted());

        verify(bookFlightService).cancelFlight(1L);
    }

    @Test
    void getBookFlight() throws Exception {
        BookFlightDto expectedResponse = BookFlightDto.builder()
                .flightName("TEST FLY")
                .build();
        when(bookFlightService.getBookFlight(1L)).thenReturn(expectedResponse);
        mockMvc.perform(get("/book-flight/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightName", is("TEST FLY")))
                .andExpect(status().is2xxSuccessful());

        verify(bookFlightService).getBookFlight(1L);
    }

    @Test
    void getAllBookFlight() throws Exception {
        final List<BookFlightDto> expectedResponse = Collections.singletonList(BookFlightDto.builder()
                .flightName("TEST FLY")
                .build());
        when(bookFlightService.getAllBookFlight()).thenReturn(expectedResponse);
        mockMvc.perform(get("/book-flight")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].flightName", is("TEST FLY")))
                .andExpect(status().is2xxSuccessful());

        verify(bookFlightService).getAllBookFlight();
    }
}