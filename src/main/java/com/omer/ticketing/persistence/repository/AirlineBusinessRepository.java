package com.omer.ticketing.persistence.repository;

import com.omer.ticketing.persistence.entity.AirlineBusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirlineBusinessRepository extends JpaRepository<AirlineBusinessEntity, Long> {

    List<AirlineBusinessEntity> findAllByNameStartingWithIgnoreCase(String keyword);
}
