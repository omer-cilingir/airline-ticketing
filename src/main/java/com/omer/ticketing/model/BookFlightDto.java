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
public class BookFlightDto {
    private Long flightId;
    private String flightName;
    private BookStatus status;
    private BigDecimal price;
    private Long id;
    private LocalDateTime createdDate;
}
