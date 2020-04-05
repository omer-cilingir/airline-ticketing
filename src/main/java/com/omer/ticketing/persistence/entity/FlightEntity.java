package com.omer.ticketing.persistence.entity;

import com.omer.ticketing.persistence.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FLIGHT")
@EqualsAndHashCode(callSuper = false)
public class FlightEntity extends BaseEntity {
    @OneToOne
    private FlyRouteEntity flyRoute;
    @OneToOne
    private AirlineBusinessEntity airlineBusiness;
    private Long remainingTicketCount;
    private Long totalTicketCount;
    private String flightDuration;
    @Column(precision = 20, scale = 5)
    private BigDecimal price;
    @Column(precision = 20, scale = 5)
    private BigDecimal initialPrice;
}
