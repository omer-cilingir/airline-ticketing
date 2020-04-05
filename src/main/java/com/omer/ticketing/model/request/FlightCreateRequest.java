package com.omer.ticketing.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightCreateRequest {
    @NotNull
    private Long flyRouteId;
    @NotNull
    private Long airlineBusinessId;
    @NotNull
    @Min(1)
    private Long ticketCount;
    @NotBlank
    private String flightDuration;
    @NotNull
    @Min(1)
    private BigDecimal price;
}
