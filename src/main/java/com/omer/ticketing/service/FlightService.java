package com.omer.ticketing.service;

import com.omer.ticketing.exception.ErrorEnum;
import com.omer.ticketing.exception.TicketingException;
import com.omer.ticketing.mapper.FlightMapper;
import com.omer.ticketing.model.FlightDto;
import com.omer.ticketing.model.request.FlightCreateRequest;
import com.omer.ticketing.persistence.entity.FlightEntity;
import com.omer.ticketing.persistence.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightMapper flightMapper;
    private final FlyRouteService flyRouteService;
    private final FlightRepository flightRepository;
    private final AirlineBusinessService airlineBusinessService;

    public static final int QUOTA_PERCENTAGE = 10;
    public static final int TICKET_RAISE_PERCENTAGE = 10;

    public void createFlight(FlightCreateRequest request) {
        verifyRequest(request);
        request.setPrice(request.getPrice().setScale(5, BigDecimal.ROUND_DOWN));
        final FlightEntity flightEntity = flightMapper.requestToEntity(request);
        flightRepository.save(flightEntity);
    }

    public List<FlightDto> getAllFlight() {
        return flightRepository.findAll().stream().map(flightMapper::entityToModel).collect(Collectors.toList());
    }

    public List<FlightDto> searchByAirlineBusinessName(String keyword) {
        return flightRepository.searchByAirlineBusinessName(keyword).stream().map(flightMapper::entityToModel).collect(Collectors.toList());
    }

    public FlightEntity findOneForUpdate(Long id) {
        return flightRepository.findOneForUpdate(id).orElseThrow(() -> new TicketingException(ErrorEnum.FLIGHT_NOT_FOUND));
    }

    public FlightEntity findOneForCancel(Long id) {
        return flightRepository.findOneForCancel(id).orElseThrow(() -> new TicketingException(ErrorEnum.FLIGHT_NOT_FOUND));
    }

    public void updateBookedFlight(FlightEntity flight, int bookedTicketCount) {
        setTicketCountAndCalculateNewPrice(flight, bookedTicketCount);
        flightRepository.save(flight);
    }

    private void setTicketCountAndCalculateNewPrice(FlightEntity flight, int bookedTicketCount) {
        flight.setRemainingTicketCount(flight.getRemainingTicketCount() - bookedTicketCount);
        final double newPercentage = calculatePercentage(flight.getRemainingTicketCount(), flight.getTotalTicketCount());
        if (newPercentage > (100 - QUOTA_PERCENTAGE)) {
            flight.setPrice(flight.getInitialPrice());
        } else {
            final int priceMultiplier = (int) ((100 - newPercentage) / QUOTA_PERCENTAGE);
            flight.setPrice(flight.getInitialPrice().add(flight.getInitialPrice().multiply(BigDecimal.valueOf(priceMultiplier)).divide(BigDecimal.valueOf(100 / TICKET_RAISE_PERCENTAGE), 5, BigDecimal.ROUND_DOWN)));
        }
    }

    private double calculatePercentage(Long obtained, Long total) {
        return obtained * 100 / total.doubleValue();
    }

    private void verifyRequest(FlightCreateRequest request) {
        flyRouteService.findById(request.getFlyRouteId());
        airlineBusinessService.findById(request.getAirlineBusinessId());
    }
}
