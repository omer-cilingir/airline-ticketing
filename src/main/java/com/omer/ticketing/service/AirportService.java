package com.omer.ticketing.service;

import com.omer.ticketing.exception.ErrorEnum;
import com.omer.ticketing.exception.TicketingException;
import com.omer.ticketing.mapper.AirportMapper;
import com.omer.ticketing.model.AirportDto;
import com.omer.ticketing.persistence.entity.AirportEntity;
import com.omer.ticketing.persistence.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    public void createAirport(AirportDto airportDto) {
        final AirportEntity airportEntity = airportMapper.toEntity(airportDto);
        airportRepository.save(airportEntity);
    }

    public List<AirportDto> getAllAirport() {
        return airportRepository.findAll().stream().map(airportMapper::toModel).collect(Collectors.toList());
    }

    public List<AirportDto> searchAirport(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return Collections.emptyList();
        }
        return airportRepository.findAllByNameStartingWithIgnoreCase(keyword).stream().map(airportMapper::toModel).collect(Collectors.toList());
    }

    public AirportEntity findById(Long id) {
        return airportRepository.findById(id).orElseThrow(() -> new TicketingException(ErrorEnum.AIRPORT_NOT_FOUND));
    }
}
