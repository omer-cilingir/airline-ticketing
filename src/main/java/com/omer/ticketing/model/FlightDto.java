package com.omer.ticketing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {
    private String fromAirportName;
    private String toAirportName;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private String airlineBusinessName;
    private Long ticketCount;
    private String flightDuration;
    private BigDecimal price;
    private Long id;
    private LocalDateTime createdDate;
}
