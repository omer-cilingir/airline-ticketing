package com.omer.ticketing.persistence.repository;

import com.omer.ticketing.persistence.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select f from FlightEntity f where f.id = :id and f.remainingTicketCount>0")
    Optional<FlightEntity> findOneForUpdate(long id);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select f from FlightEntity f where f.id = :id")
    Optional<FlightEntity> findOneForCancel(long id);

    @Query("select distinct f from FlightEntity f join fetch f.airlineBusiness ab join fetch f.flyRoute fr join fetch fr.from a1 join fetch fr.to a2")
    List<FlightEntity> findAll();

    @Query("select distinct f from FlightEntity f join fetch f.airlineBusiness ab join fetch f.flyRoute fr join fetch fr.from a1 join fetch fr.to a2" +
            " where lower(ab.name) like lower(concat(:keyword,'%')) escape '\\'")
    List<FlightEntity> searchByAirlineBusinessName(String keyword);
}
