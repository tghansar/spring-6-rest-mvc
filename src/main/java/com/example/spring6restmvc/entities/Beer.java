package com.example.spring6restmvc.entities;

import com.example.spring6restmvc.enums.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
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
@ToString
public class Beer {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotBlank
    @NotNull
    @Size(max = 50) //bean validation constraint
    @Column(length = 50) // database table configuration
    private String beerName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BeerStyle beerStyle;

    @NotBlank
    @NotNull
//    @Column(nullable = false, unique = true)
    private String upc;
    private Integer quantityOnHand;

    @NotNull
//    @Column(nullable = false)
    private BigDecimal price;

//    @Column(columnDefinition = "timestamp", updatable = false)
    private LocalDateTime createdDate;

//    @Column(columnDefinition = "timestamp")
    private LocalDateTime updateDate;
}