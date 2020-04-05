package com.omer.ticketing.service;

import com.omer.ticketing.exception.ErrorEnum;
import com.omer.ticketing.exception.TicketingException;
import com.omer.ticketing.mapper.AirlineBusinessMapper;
import com.omer.ticketing.model.AirlineBusinessDto;
import com.omer.ticketing.persistence.entity.AirlineBusinessEntity;
import com.omer.ticketing.persistence.repository.AirlineBusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirlineBusinessService {

    private final AirlineBusinessRepository airlineBusinessRepository;
    private final AirlineBusinessMapper airlineBusinessMapper;

    public void createAirlineBusiness(AirlineBusinessDto airlineBusinessDto) {
        final AirlineBusinessEntity airlineBusinessEntity = airlineBusinessMapper.toEntity(airlineBusinessDto);
        airlineBusinessRepository.save(airlineBusinessEntity);
    }

    public List<AirlineBusinessDto> getAllAirlineBusiness() {
        return airlineBusinessRepository.findAll().stream().map(airlineBusinessMapper::toModel).collect(Collectors.toList());
    }

    public List<AirlineBusinessDto> searchAirlineBusiness(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return Collections.emptyList();
        }
        return airlineBusinessRepository.findAllByNameStartingWithIgnoreCase(keyword).stream().map(airlineBusinessMapper::toModel).collect(Collectors.toList());
    }

    public AirlineBusinessEntity findById(Long id) {
        return airlineBusinessRepository.findById(id).orElseThrow(() -> new TicketingException(ErrorEnum.AIRLINE_BUSINESS_NOT_FOUND));
    }
}
