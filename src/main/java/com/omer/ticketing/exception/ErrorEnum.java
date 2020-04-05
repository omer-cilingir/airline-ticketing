package com.omer.ticketing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    GENERAL_EXCEPTION(1, "Something went wrong!"),
    REQUIRED_PARAMETERS_CANNOT_BE_NULL(2, "Required parameters cannot be null"),
    AIRPORT_NOT_FOUND(3, "Airport not found"),
    FLY_ROUTE_NOT_FOUND(4, "Fly route not found"),
    AIRLINE_BUSINESS_NOT_FOUND(5, "Airline business not found"),
    FLIGHT_NOT_FOUND(6, "Flight not found"),
    BOOKED_FLIGHT_NOT_FOUND(7, "Booked flight not found");

    private int code;
    private String message;
}
