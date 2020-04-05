package com.omer.ticketing.web;

import com.omer.ticketing.model.AirportDto;
import com.omer.ticketing.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/airport")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAirport(@RequestBody @Valid AirportDto airportDto) {
        airportService.createAirport(airportDto);
    }

    @GetMapping("/search")
    public List<AirportDto> searchAirport(@RequestParam String keyword) {
        return airportService.searchAirport(keyword);
    }

    @GetMapping
    public List<AirportDto> findAll() {
        return airportService.getAllAirport();
    }

}
