package com.omer.ticketing.persistence.entity;

import com.omer.ticketing.persistence.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AIRLINE_BUSINESS")
@EqualsAndHashCode(callSuper = false)
public class AirlineBusinessEntity extends BaseEntity {
    private String name;
}
