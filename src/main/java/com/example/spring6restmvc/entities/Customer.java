package com.example.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2023/09/21, Thu, 09:59
 **/

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "id", length = 36, columnDefinition = "uuid", updatable = false, nullable = false)
//    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "created_date", columnDefinition = "timestamp")
    private LocalDateTime createdDate;

    @Column(name = "update_date", columnDefinition = "timestamp")
    private LocalDateTime updateDate;
}

