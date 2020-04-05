package com.omer.ticketing.persistence.repository;

import com.omer.ticketing.persistence.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<AirportEntity, Long> {

    List<AirportEntity> findAllByNameStartingWithIgnoreCase(String keyword);
}
