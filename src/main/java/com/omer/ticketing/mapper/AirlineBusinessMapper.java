package com.omer.ticketing.mapper;

import com.omer.ticketing.model.AirlineBusinessDto;
import com.omer.ticketing.persistence.entity.AirlineBusinessEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirlineBusinessMapper {

    AirlineBusinessEntity toEntity(AirlineBusinessDto model);

    AirlineBusinessDto toModel(AirlineBusinessEntity entity);
}
