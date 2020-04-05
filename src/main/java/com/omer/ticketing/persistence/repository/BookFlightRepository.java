package com.omer.ticketing.persistence.repository;

import com.omer.ticketing.model.BookStatus;
import com.omer.ticketing.persistence.entity.BookFlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookFlightRepository extends JpaRepository<BookFlightEntity, Long> {

    Optional<BookFlightEntity> findByIdAndStatus(Long id, BookStatus status);
}
