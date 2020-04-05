package com.omer.ticketing.mapper;

import com.omer.ticketing.model.BookFlightDto;
import com.omer.ticketing.persistence.entity.BookFlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookFlightMapper {

    @Mapping(source = "flightEntity.id", target = "flightId")
    @Mapping(source = "flightEntity.airlineBusiness.name", target = "flightName")
    BookFlightDto entityToModel(BookFlightEntity entity);
}
