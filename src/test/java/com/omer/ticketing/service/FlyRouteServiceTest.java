package com.omer.ticketing.service;

import com.omer.ticketing.mapper.FlyRouteMapper;
import com.omer.ticketing.model.FlyRouteDto;
import com.omer.ticketing.model.request.FlyRouteCreateRequest;
import com.omer.ticketing.persistence.entity.AirportEntity;
import com.omer.ticketing.persistence.entity.FlyRouteEntity;
import com.omer.ticketing.persistence.repository.FlyRouteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlyRouteServiceTest {
    @Mock
    private AirportService airportService;
    @Spy
    private FlyRouteMapper flyRouteMapper = Mappers.getMapper(FlyRouteMapper.class);
    @Mock
    private FlyRouteRepository flyRouteRepository;
    @InjectMocks
    private FlyRouteService flyRouteService;
    @Captor
    private ArgumentCaptor<FlyRouteEntity> flyRouteEntityArgumentCaptor;

    @Test
    void createFlyRoute() {
        FlyRouteCreateRequest request = FlyRouteCreateRequest.builder()
                .fromAirportId(1L)
                .toAirportId(2L)
                .build();
        when(airportService.findById(any())).thenReturn(any());
        flyRouteService.createFlyRoute(request);

        verify(flyRouteRepository).save(flyRouteEntityArgumentCaptor.capture());

        assertEquals(1L, flyRouteEntityArgumentCaptor.getValue().getFrom().getId());
        assertEquals(2L, flyRouteEntityArgumentCaptor.getValue().getTo().getId());
    }

    @Test
    void getAllFlyRoute() {
        List<FlyRouteEntity> flyRouteEntities = Collections.singletonList(FlyRouteEntity.builder()
                .from(AirportEntity.builder().name("IST").build())
                .to(AirportEntity.builder().name("TRB").build())
                .build());
        when(flyRouteRepository.findAll()).thenReturn(flyRouteEntities);
        final List<FlyRouteDto> allFlyRoute = flyRouteService.getAllFlyRoute();

        assertEquals("IST", allFlyRoute.get(0).getFromAirportName());
        assertEquals("TRB", allFlyRoute.get(0).getToAirportName());
    }

    @Test
    void searchByFromName() {
        List<FlyRouteEntity> flyRouteEntities = Collections.singletonList(FlyRouteEntity.builder()
                .from(AirportEntity.builder().name("IST").build())
                .to(AirportEntity.builder().name("TRB").build())
                .build());
        when(flyRouteRepository.searchByFromName("IST")).thenReturn(flyRouteEntities);
        final List<FlyRouteDto> allFlyRoute = flyRouteService.searchByFromName("IST");

        assertEquals("IST", allFlyRoute.get(0).getFromAirportName());
        assertEquals("TRB", allFlyRoute.get(0).getToAirportName());
    }

    @Test
    void searchByFromName_emptyName() {
        final List<FlyRouteDto> allFlyRoute = flyRouteService.searchByFromName("");
        assertEquals(0, allFlyRoute.size());
    }

    @Test
    void findById() {
        final FlyRouteEntity flyRoute = FlyRouteEntity.builder()
                .from(AirportEntity.builder().name("IST").build())
                .to(AirportEntity.builder().name("TRB").build())
                .build();
        when(flyRouteRepository.findById(1L)).thenReturn(Optional.of(flyRoute));
        final FlyRouteEntity flyRouteEntity = flyRouteService.findById(1L);

        assertEquals("IST", flyRouteEntity.getFrom().getName());
        assertEquals("TRB", flyRouteEntity.getTo().getName());
    }
}