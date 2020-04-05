package com.omer.ticketing.mapper;

import com.omer.ticketing.model.FlightDto;
import com.omer.ticketing.model.request.FlightCreateRequest;
import com.omer.ticketing.persistence.entity.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(source = "flyRouteId", target = "flyRoute.id")
    @Mapping(source = "ticketCount", target = "remainingTicketCount")
    @Mapping(source = "ticketCount", target = "totalTicketCount")
    @Mapping(source = "airlineBusinessId", target = "airlineBusiness.id")
    @Mapping(source = "price", target = "initialPrice")
    FlightEntity requestToEntity(FlightCreateRequest request);

    @Mapping(source = "flyRoute.from.name", target = "fromAirportName")
    @Mapping(source = "flyRoute.to.name", target = "toAirportName")
    @Mapping(source = "flyRoute.departure", target = "departure")
    @Mapping(source = "flyRoute.arrival", target = "arrival")
    @Mapping(source = "airlineBusiness.name", target = "airlineBusinessName")
    @Mapping(source = "remainingTicketCount", target = "ticketCount")
    FlightDto entityToModel(FlightEntity entity);
}
