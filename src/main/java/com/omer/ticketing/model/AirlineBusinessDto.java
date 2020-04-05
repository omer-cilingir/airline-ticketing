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
public class AirlineBusinessDto {
    private String name;
    private Long id;
    private LocalDateTime createdDate;
}
