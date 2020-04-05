package com.omer.ticketing.mapper;

import com.omer.ticketing.model.FlyRouteDto;
import com.omer.ticketing.model.request.FlyRouteCreateRequest;
import com.omer.ticketing.persistence.entity.FlyRouteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlyRouteMapper {

    @Mapping(source = "fromAirportId", target = "from.id")
    @Mapping(source = "toAirportId", target = "to.id")
    FlyRouteEntity requestToEntity(FlyRouteCreateRequest request);

    @Mapping(source = "from.name", target = "fromAirportName")
    @Mapping(source = "to.name", target = "toAirportName")
    FlyRouteDto entityToModel(FlyRouteEntity entity);
}
