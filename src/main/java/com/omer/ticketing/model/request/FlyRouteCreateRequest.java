package com.omer.ticketing.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlyRouteCreateRequest {
    @NotNull
    private Long fromAirportId;
    @NotNull
    private Long toAirportId;
    @NotNull
    private LocalDateTime departure;
    @NotNull
    private LocalDateTime arrival;
}
