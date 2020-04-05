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
@Table(name = "AIRPORT")
@EqualsAndHashCode(callSuper = false)
public class AirportEntity extends BaseEntity {
    private String name;
    private String code;
}
