package com.omer.ticketing.web;

import com.omer.ticketing.model.FlyRouteDto;
import com.omer.ticketing.model.request.FlyRouteCreateRequest;
import com.omer.ticketing.service.FlyRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fly-route")
@RequiredArgsConstructor
public class FlyRouteController {
    private final FlyRouteService flyRouteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFlyRoute(@RequestBody FlyRouteCreateRequest request) {
        flyRouteService.createFlyRoute(request);
    }

    @GetMapping
    public List<FlyRouteDto> getAllFlyRoute() {
        return flyRouteService.getAllFlyRoute();
    }

    @GetMapping("/search/from")
    public List<FlyRouteDto> searchByFromName(@RequestParam String keyword) {
        return flyRouteService.searchByFromName(keyword);
    }
}
