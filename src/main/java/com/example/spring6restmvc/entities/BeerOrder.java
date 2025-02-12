package com.example.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2025/01/21, Tue, 14:37
 **/

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrder {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false )
    private Long id;

    @Version
    private Integer version;

    @Column(name="customer_ref")
    private String customerRef;

    @Column(name = "created_date", columnDefinition = "timestamp")
    private LocalDateTime createdDate;

    @Column(name = "update_date", columnDefinition = "timestamp")
    private LocalDateTime updateDate;
}
