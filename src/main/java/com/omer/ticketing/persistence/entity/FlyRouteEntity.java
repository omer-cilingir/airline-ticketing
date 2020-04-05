package com.omer.ticketing.persistence.entity;

import com.omer.ticketing.persistence.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FLY_ROUTE")
@EqualsAndHashCode(callSuper = false)
public class FlyRouteEntity extends BaseEntity {
    @OneToOne
    private AirportEntity from;
    @OneToOne
    private AirportEntity to;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
