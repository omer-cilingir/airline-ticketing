package com.omer.ticketing.persistence.repository;

import com.omer.ticketing.persistence.entity.FlyRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlyRouteRepository extends JpaRepository<FlyRouteEntity, Long> {

    @Query("select distinct f from FlyRouteEntity f join fetch f.from a1 join fetch f.to a2")
    List<FlyRouteEntity> findAll();

    @Query("select distinct f from FlyRouteEntity f join fetch f.from a1 join fetch f.to a2 where lower(a1.name) like lower(concat(:keyword,'%')) escape '\\'")
    List<FlyRouteEntity> searchByFromName(String keyword);
}
