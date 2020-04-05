package com.omer.ticketing.web;

import com.omer.ticketing.model.FlightDto;
import com.omer.ticketing.model.request.FlightCreateRequest;
import com.omer.ticketing.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFlight(@RequestBody FlightCreateRequest request) {
        flightService.createFlight(request);
    }

    @GetMapping
    public List<FlightDto> getAllFlight() {
        return flightService.getAllFlight();
    }

    @GetMapping("/search")
    public List<FlightDto> searchByAirlineBusinessName(@RequestParam String keyword) {
        return flightService.searchByAirlineBusinessName(keyword);
    }
}
