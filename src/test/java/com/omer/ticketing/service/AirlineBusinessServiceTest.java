package com.omer.ticketing.service;

import com.omer.ticketing.mapper.AirlineBusinessMapper;
import com.omer.ticketing.model.AirlineBusinessDto;
import com.omer.ticketing.persistence.entity.AirlineBusinessEntity;
import com.omer.ticketing.persistence.repository.AirlineBusinessRepository;
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
class AirlineBusinessServiceTest {

    @Mock
    private AirlineBusinessRepository airlineBusinessRepository;
    @Spy
    private AirlineBusinessMapper airlineBusinessMapper = Mappers.getMapper(AirlineBusinessMapper.class);
    @InjectMocks
    private AirlineBusinessService airlineBusinessService;
    @Captor
    private ArgumentCaptor<AirlineBusinessEntity> airlineBusinessEntityArgumentCaptor;

    @Test
    void createAirlineBusiness() {
        final AirlineBusinessDto request = AirlineBusinessDto.builder().name("THY").build();

        airlineBusinessService.createAirlineBusiness(request);

        verify(airlineBusinessRepository).save(airlineBusinessEntityArgumentCaptor.capture());

        assertEquals("THY", airlineBusinessEntityArgumentCaptor.getValue().getName());
    }

    @Test
    void getAllAirlineBusiness() {

        List<AirlineBusinessEntity> expectedResponse = Collections.singletonList(AirlineBusinessEntity.builder()
                .name("THY")
                .build());
        when(airlineBusinessRepository.findAll()).thenReturn(expectedResponse);

        final List<AirlineBusinessDto> allAirlineBusiness = airlineBusinessService.getAllAirlineBusiness();

        assertEquals("THY", allAirlineBusiness.get(0).getName());
    }

    @Test
    void searchAirlineBusiness() {

        List<AirlineBusinessEntity> expectedResponse = Collections.singletonList(AirlineBusinessEntity.builder()
                .name("THY")
                .build());
        when(airlineBusinessRepository.findAllByNameStartingWithIgnoreCase("T")).thenReturn(expectedResponse);

        final List<AirlineBusinessDto> allAirlineBusiness = airlineBusinessService.searchAirlineBusiness("T");

        assertEquals("THY", allAirlineBusiness.get(0).getName());
    }

    @Test
    void searchAirlineBusiness_emptyKeyword() {

        final List<AirlineBusinessDto> allAirlineBusiness = airlineBusinessService.searchAirlineBusiness("");

        assertEquals(0, allAirlineBusiness.size());
    }

    @Test
    void findById() {
        final AirlineBusinessEntity expectedResponse = AirlineBusinessEntity.builder()
                .name("THY")
                .build();
        when(airlineBusinessRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));

        final AirlineBusinessEntity airlineBusinessEntity = airlineBusinessService.findById(1L);

        assertEquals("THY", airlineBusinessEntity.getName());
    }
}