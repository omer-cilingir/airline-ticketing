package com.omer.ticketing.persistence.entity;

import com.omer.ticketing.model.BookStatus;
import com.omer.ticketing.persistence.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOK_FLIGHT")
@EqualsAndHashCode(callSuper = false)
public class BookFlightEntity extends BaseEntity {
    @OneToOne
    private FlightEntity flightEntity;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Column(precision = 20, scale = 5)
    private BigDecimal price;
}
