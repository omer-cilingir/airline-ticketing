package com.omer.ticketing.mapper;

import com.omer.ticketing.model.AirportDto;
import com.omer.ticketing.persistence.entity.AirportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportEntity toEntity(AirportDto model);

    AirportDto toModel(AirportEntity entity);
}
