package com.omer.ticketing.service;

import com.omer.ticketing.exception.ErrorEnum;
import com.omer.ticketing.exception.TicketingException;
import com.omer.ticketing.mapper.FlyRouteMapper;
import com.omer.ticketing.model.FlyRouteDto;
import com.omer.ticketing.model.request.FlyRouteCreateRequest;
import com.omer.ticketing.persistence.entity.FlyRouteEntity;
import com.omer.ticketing.persistence.repository.FlyRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlyRouteService {
    private final AirportService airportService;
    private final FlyRouteMapper flyRouteMapper;
    private final FlyRouteRepository flyRouteRepository;

    public void createFlyRoute(FlyRouteCreateRequest request) {
        verifyAirportsIsValid(request);
        final FlyRouteEntity flyRouteEntity = flyRouteMapper.requestToEntity(request);
        flyRouteRepository.save(flyRouteEntity);
    }

    public List<FlyRouteDto> getAllFlyRoute() {
        return flyRouteRepository.findAll().stream().map(flyRouteMapper::entityToModel).collect(Collectors.toList());
    }

    public List<FlyRouteDto> searchByFromName(String fromName) {
        if (StringUtils.isEmpty(fromName)) {
            return Collections.emptyList();
        }
        return flyRouteRepository.searchByFromName(fromName).stream().map(flyRouteMapper::entityToModel).collect(Collectors.toList());

    }

    public FlyRouteEntity findById(Long id) {
        return flyRouteRepository.findById(id).orElseThrow(() -> new TicketingException(ErrorEnum.FLY_ROUTE_NOT_FOUND));
    }

    private void verifyAirportsIsValid(FlyRouteCreateRequest request) {
        airportService.findById(request.getFromAirportId());
        airportService.findById(request.getToAirportId());
    }
}
