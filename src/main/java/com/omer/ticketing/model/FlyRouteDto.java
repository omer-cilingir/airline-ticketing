package com.omer.ticketing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlyRouteDto {
    private String fromAirportName;
    private String toAirportName;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private Long id;
    private LocalDateTime createdDate;
}
