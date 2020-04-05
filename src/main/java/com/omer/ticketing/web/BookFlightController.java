package com.omer.ticketing.web;

import com.omer.ticketing.model.BookFlightDto;
import com.omer.ticketing.model.request.BookFlightRequest;
import com.omer.ticketing.service.BookFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-flight")
@RequiredArgsConstructor
public class BookFlightController {

    private final BookFlightService bookFlightService;

    @PostMapping
    public BookFlightDto bookFlight(@RequestBody BookFlightRequest request) {
        return bookFlightService.bookFlight(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelBookFlight(@RequestParam Long bookFlightId) {
        bookFlightService.cancelFlight(bookFlightId);
    }

    @GetMapping("/{bookFlightId}")
    public BookFlightDto getBookFlight(@PathVariable Long bookFlightId) {
        return bookFlightService.getBookFlight(bookFlightId);
    }

    @GetMapping
    public List<BookFlightDto> getAllBookFlight() {
        return bookFlightService.getAllBookFlight();
    }
}
