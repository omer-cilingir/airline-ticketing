package com.omer.ticketing.service;

import com.omer.ticketing.mapper.AirportMapper;
import com.omer.ticketing.model.AirportDto;
import com.omer.ticketing.persistence.entity.AirportEntity;
import com.omer.ticketing.persistence.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;
    @Spy
    private AirportMapper airportMapper = Mappers.getMapper(AirportMapper.class);
    @InjectMocks
    private AirportService airportService;
    @Captor
    private ArgumentCaptor<AirportEntity> airportEntityArgumentCaptor;

    @Test
    void createAirport() {
        final AirportDto request = AirportDto.builder().name("TRABZON").build();

        airportService.createAirport(request);

        verify(airportRepository).save(airportEntityArgumentCaptor.capture());

        assertEquals("TRABZON", airportEntityArgumentCaptor.getValue().getName());
    }

    @Test
    void getAllAirport() {

        List<AirportEntity> expectedResponse = Collections.singletonList(AirportEntity.builder()
                .name("TRABZON")
                .build());
        when(airportRepository.findAll()).thenReturn(expectedResponse);

        final List<AirportDto> allAirport = airportService.getAllAirport();

        assertEquals("TRABZON", allAirport.get(0).getName());
    }

    @Test
    void searchAirport() {

        List<AirportEntity> expectedResponse = Collections.singletonList(AirportEntity.builder()
                .name("TRABZON")
                .build());
        when(airportRepository.findAllByNameStartingWithIgnoreCase("T")).thenReturn(expectedResponse);

        final List<AirportDto> airportDtos = airportService.searchAirport("T");

        assertEquals("TRABZON", airportDtos.get(0).getName());
    }

    @Test
    void searchAirport_emptyKeyword() {

        final List<AirportDto> airportDtos = airportService.searchAirport("");

        assertEquals(0, airportDtos.size());
    }

    @Test
    void findById() {
        final AirportEntity expectedResponse = AirportEntity.builder()
                .name("TRABZON")
                .build();
        when(airportRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));

        final AirportEntity airportEntity = airportService.findById(1L);

        assertEquals("TRABZON", airportEntity.getName());
    }
}