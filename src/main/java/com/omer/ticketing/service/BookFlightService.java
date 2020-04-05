package com.omer.ticketing.service;

import com.omer.ticketing.exception.ErrorEnum;
import com.omer.ticketing.exception.TicketingException;
import com.omer.ticketing.mapper.BookFlightMapper;
import com.omer.ticketing.model.BookFlightDto;
import com.omer.ticketing.model.BookStatus;
import com.omer.ticketing.model.request.BookFlightRequest;
import com.omer.ticketing.persistence.entity.BookFlightEntity;
import com.omer.ticketing.persistence.entity.FlightEntity;
import com.omer.ticketing.persistence.repository.BookFlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookFlightService {

    private final FlightService flightService;
    private final BookFlightMapper bookFlightMapper;
    private final BookFlightRepository bookFlightRepository;

    @Transactional
    public BookFlightDto bookFlight(BookFlightRequest request) {
        final FlightEntity flight = flightService.findOneForUpdate(request.getFlightId());
        BookFlightEntity bookFlight = BookFlightEntity.builder()
                .flightEntity(flight)
                .status(BookStatus.BOOKED)
                .price(flight.getPrice())
                .build();
        final BookFlightEntity bookFlightEntity = bookFlightRepository.save(bookFlight);
        flightService.updateBookedFlight(flight, 1);
        return bookFlightMapper.entityToModel(bookFlightEntity);
    }

    @Transactional
    public void cancelFlight(Long id) {
        final BookFlightEntity bookedFlight = bookFlightRepository.findByIdAndStatus(id, BookStatus.BOOKED).orElseThrow(() -> new TicketingException(ErrorEnum.BOOKED_FLIGHT_NOT_FOUND));
        final FlightEntity flight = flightService.findOneForCancel(bookedFlight.getFlightEntity().getId());
        bookedFlight.setStatus(BookStatus.CANCEL);
        bookFlightRepository.save(bookedFlight);
        flightService.updateBookedFlight(flight, -1);
    }

    @Transactional
    public BookFlightDto getBookFlight(Long id) {
        final BookFlightEntity bookedFlight = bookFlightRepository.findById(id).orElseThrow(() -> new TicketingException(ErrorEnum.BOOKED_FLIGHT_NOT_FOUND));
        return bookFlightMapper.entityToModel(bookedFlight);
    }

    public List<BookFlightDto> getAllBookFlight() {
        return bookFlightRepository.findAll().stream().map(bookFlightMapper::entityToModel).collect(Collectors.toList());
    }
}
