package com.omer.ticketing.service;

import com.omer.ticketing.mapper.BookFlightMapper;
import com.omer.ticketing.model.BookFlightDto;
import com.omer.ticketing.model.BookStatus;
import com.omer.ticketing.model.request.BookFlightRequest;
import com.omer.ticketing.persistence.entity.BookFlightEntity;
import com.omer.ticketing.persistence.entity.FlightEntity;
import com.omer.ticketing.persistence.repository.BookFlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookFlightServiceTest {
    @Mock
    private BookFlightRepository bookFlightRepository;
    @Mock
    private FlightService flightService;
    @Spy
    private BookFlightMapper bookFlightMapper = Mappers.getMapper(BookFlightMapper.class);
    @InjectMocks
    private BookFlightService bookFlightService;
    @Captor
    private ArgumentCaptor<BookFlightEntity> bookFlightEntityArgumentCaptor;

    @Test
    void bookFlight() {
        BookFlightRequest request = BookFlightRequest.builder().flightId(1L).build();
        FlightEntity flightEntity = FlightEntity.builder()
                .price(BigDecimal.TEN)
                .totalTicketCount(20L)
                .build();
        BookFlightEntity bookFlightEntity = BookFlightEntity.builder()
                .price(BigDecimal.TEN)
                .build();
        when(flightService.findOneForUpdate(request.getFlightId())).thenReturn(flightEntity);
        doReturn(bookFlightEntity).when(bookFlightRepository).save(any());

        final BookFlightDto bookedFlight = bookFlightService.bookFlight(request);

        verify(flightService).findOneForUpdate(request.getFlightId());
        verify(flightService).updateBookedFlight(any(), eq(1));

        assertEquals(bookedFlight.getPrice(), flightEntity.getPrice());
    }

    @Test
    void cancelFlight() {
        BookFlightRequest request = BookFlightRequest.builder().flightId(1L).build();
        FlightEntity flightEntity = FlightEntity.builder()
                .price(BigDecimal.TEN)
                .totalTicketCount(20L)
                .build();
        flightEntity.setId(1L);
        BookFlightEntity bookFlightEntity = BookFlightEntity.builder()
                .flightEntity(flightEntity)
                .price(BigDecimal.TEN)
                .build();
        when(bookFlightRepository.findByIdAndStatus(request.getFlightId(), BookStatus.BOOKED)).thenReturn(Optional.of(bookFlightEntity));
        when(flightService.findOneForCancel(request.getFlightId())).thenReturn(flightEntity);

        bookFlightService.cancelFlight(1L);

        verify(bookFlightRepository).save(bookFlightEntityArgumentCaptor.capture());
        verify(flightService).updateBookedFlight(flightEntity, -1);

        assertEquals(BookStatus.CANCEL, bookFlightEntityArgumentCaptor.getValue().getStatus());
    }

    @Test
    void getBookFlight() {
        BookFlightEntity bookFlight = BookFlightEntity.builder()
                .price(BigDecimal.TEN)
                .status(BookStatus.BOOKED)
                .build();
        when(bookFlightRepository.findById(1L)).thenReturn(Optional.of(bookFlight));

        final BookFlightDto response = bookFlightService.getBookFlight(1L);

        assertEquals(response.getStatus(), bookFlight.getStatus());
        assertEquals(response.getPrice(), bookFlight.getPrice());

    }

    @Test
    void getAllBookFlight() {
        final List<BookFlightEntity> bookFlightEntities = Collections.singletonList(BookFlightEntity.builder()
                .price(BigDecimal.TEN)
                .status(BookStatus.BOOKED)
                .build());
        when(bookFlightRepository.findAll()).thenReturn(bookFlightEntities);

        final List<BookFlightDto> response = bookFlightService.getAllBookFlight();

        assertEquals(response.get(0).getStatus(), bookFlightEntities.get(0).getStatus());
        assertEquals(response.get(0).getPrice(), bookFlightEntities.get(0).getPrice());
    }
}