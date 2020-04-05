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
public class AirportDto {
    private String name;
    private String code;
    private Long id;
    private LocalDateTime createdDate;
}
