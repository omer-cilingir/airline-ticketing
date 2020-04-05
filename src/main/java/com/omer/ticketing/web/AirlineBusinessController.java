package com.omer.ticketing.web;

import com.omer.ticketing.model.AirlineBusinessDto;
import com.omer.ticketing.service.AirlineBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airline-business")
@RequiredArgsConstructor
public class AirlineBusinessController {

    private final AirlineBusinessService airlineBusinessService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAirlineBusiness(@RequestBody AirlineBusinessDto airlineBusinessDto) {
        airlineBusinessService.createAirlineBusiness(airlineBusinessDto);
    }

    @GetMapping
    public List<AirlineBusinessDto> getAllAirlineBusiness() {
        return airlineBusinessService.getAllAirlineBusiness();
    }

    @GetMapping("/search")
    public List<AirlineBusinessDto> searchAirlineBusiness(@RequestParam String keyword) {
        return airlineBusinessService.searchAirlineBusiness(keyword);
    }
}
