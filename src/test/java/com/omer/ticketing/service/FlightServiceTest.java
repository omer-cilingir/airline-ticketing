package com.omer.ticketing.service;

import com.omer.ticketing.mapper.FlightMapper;
import com.omer.ticketing.model.FlightDto;
import com.omer.ticketing.model.request.FlightCreateRequest;
import com.omer.ticketing.persistence.entity.FlightEntity;
import com.omer.ticketing.persistence.repository.FlightRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
    @Spy
    private FlightMapper flightMapper = Mappers.getMapper(FlightMapper.class);
    @Mock
    private FlyRouteService flyRouteService;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private AirlineBusinessService airlineBusinessService;
    @InjectMocks
    private FlightService flightService;
    @Captor
    private ArgumentCaptor<FlightEntity> flightEntityArgumentCaptor;

    @Test
    void createFlight() {
        FlightCreateRequest request = FlightCreateRequest.builder()
                .airlineBusinessId(1L)
                .flyRouteId(1L)
                .ticketCount(20L)
                .price(BigDecimal.TEN)
                .build();
        when(flyRouteService.findById(1L)).thenReturn(any());
        when(airlineBusinessService.findById(1L)).thenReturn(any());

        flightService.createFlight(request);

        verify(flightRepository).save(flightEntityArgumentCaptor.capture());

        assertEquals(20L, flightEntityArgumentCaptor.getValue().getTotalTicketCount());
        assertEquals(BigDecimal.TEN.setScale(5, BigDecimal.ROUND_DOWN), flightEntityArgumentCaptor.getValue().getPrice());
    }

    @Test
    void getAllFlight() {
        final List<FlightEntity> flightEntities = Collections.singletonList(FlightEntity.builder()
                .totalTicketCount(20L)
                .remainingTicketCount(20L)
                .price(BigDecimal.TEN)
                .build());

        when(flightRepository.findAll()).thenReturn(flightEntities);

        final List<FlightDto> allFlight = flightService.getAllFlight();

        assertEquals(allFlight.get(0).getTicketCount(), flightEntities.get(0).getTotalTicketCount());
        assertEquals(allFlight.get(0).getPrice(), flightEntities.get(0).getPrice());
    }

    @Test
    void searchByAirlineBusinessName() {

        final List<FlightEntity> flightEntities = Collections.singletonList(FlightEntity.builder()
                .totalTicketCount(20L)
                .remainingTicketCount(20L)
                .price(BigDecimal.TEN)
                .build());

        when(flightRepository.searchByAirlineBusinessName("T")).thenReturn(flightEntities);

        final List<FlightDto> allFlight = flightService.searchByAirlineBusinessName("T");

        assertEquals(allFlight.get(0).getTicketCount(), flightEntities.get(0).getTotalTicketCount());
        assertEquals(allFlight.get(0).getPrice(), flightEntities.get(0).getPrice());
    }

    @Test
    void findOneForUpdate() {
        FlightEntity flightEntity = FlightEntity.builder()
                .remainingTicketCount(20L)
                .price(BigDecimal.TEN)
                .build();
        when(flightRepository.findOneForUpdate(1L)).thenReturn(Optional.of(flightEntity));

        final FlightEntity oneForUpdate = flightService.findOneForUpdate(1L);
        assertEquals(flightEntity.getRemainingTicketCount(), oneForUpdate.getRemainingTicketCount());
        assertEquals(flightEntity.getPrice(), oneForUpdate.getPrice());
    }

    @Test
    void findOneForCancel() {
        FlightEntity flightEntity = FlightEntity.builder()
                .remainingTicketCount(20L)
                .price(BigDecimal.TEN)
                .build();
        when(flightRepository.findOneForCancel(1L)).thenReturn(Optional.of(flightEntity));

        final FlightEntity oneForUpdate = flightService.findOneForCancel(1L);
        assertEquals(flightEntity.getRemainingTicketCount(), oneForUpdate.getRemainingTicketCount());
        assertEquals(flightEntity.getPrice(), oneForUpdate.getPrice());
    }

    @Test
    void updateBookedFlight_notDiscount() {
        FlightEntity flight = FlightEntity.builder()
                .remainingTicketCount(20L)
                .totalTicketCount(20L)
                .price(BigDecimal.valueOf(100))
                .initialPrice(BigDecimal.valueOf(100))
                .build();
        flightService.updateBookedFlight(flight, 1);
        verify(flightRepository).save(flightEntityArgumentCaptor.capture());

        assertEquals(BigDecimal.valueOf(100), flightEntityArgumentCaptor.getValue().getPrice());
        assertEquals(19L, flightEntityArgumentCaptor.getValue().getRemainingTicketCount());
    }

    @Test
    void updateBookedFlight_Discount10() {
        FlightEntity flight = FlightEntity.builder()
                .remainingTicketCount(10L)
                .totalTicketCount(10L)
                .price(BigDecimal.valueOf(100))
                .initialPrice(BigDecimal.valueOf(100))
                .build();
        flightService.updateBookedFlight(flight, 1);
        verify(flightRepository).save(flightEntityArgumentCaptor.capture());

        assertEquals(BigDecimal.valueOf(110).setScale(5, BigDecimal.ROUND_DOWN), flightEntityArgumentCaptor.getValue().getPrice());
        assertEquals(9L, flightEntityArgumentCaptor.getValue().getRemainingTicketCount());
    }

    @Test
    void updateBookedFlight_cancel() {
        FlightEntity flight = FlightEntity.builder()
                .remainingTicketCount(19L)
                .totalTicketCount(20L)
                .price(BigDecimal.valueOf(100))
                .initialPrice(BigDecimal.valueOf(100))
                .build();
        flightService.updateBookedFlight(flight, -1);
        verify(flightRepository).save(flightEntityArgumentCaptor.capture());

        assertEquals(BigDecimal.valueOf(100), flightEntityArgumentCaptor.getValue().getPrice());
        assertEquals(20L, flightEntityArgumentCaptor.getValue().getRemainingTicketCount());
    }

    @Test
    void updateBookedFlight_cancel10() {
        FlightEntity flight = FlightEntity.builder()
                .remainingTicketCount(9L)
                .totalTicketCount(10L)
                .price(BigDecimal.valueOf(110))
                .initialPrice(BigDecimal.valueOf(100))
                .build();
        flightService.updateBookedFlight(flight, -1);
        verify(flightRepository).save(flightEntityArgumentCaptor.capture());

        assertEquals(BigDecimal.valueOf(100), flightEntityArgumentCaptor.getValue().getPrice());
        assertEquals(10L, flightEntityArgumentCaptor.getValue().getRemainingTicketCount());
    }
}