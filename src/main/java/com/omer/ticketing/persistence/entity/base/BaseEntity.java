package com.omer.ticketing.persistence.entity.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime modifiedDate;
    Boolean deleted = false;
}
