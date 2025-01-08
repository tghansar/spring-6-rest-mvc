package com.example.spring6restmvc.entities;

import com.example.spring6restmvc.enums.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

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
    @Column(name = "id", length = 36, columnDefinition = "uuid", updatable = false, nullable = false)
//    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Version
    private Integer version;

    @NotBlank
    @NotNull
    @Size(max = 50) //bean validation constraint
    @Column(name = "beer_name", length = 50) // database table configuration
    private String beerName;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(value = SqlTypes.SMALLINT)
    @NotNull
    @Column(name = "beer_style")
    private BeerStyle beerStyle;

    @NotBlank
    @NotNull
    @Column(name = "upc")
    private String upc;

    @Column(name = "quantity_on_hand")
    private Integer quantityOnHand;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_date", columnDefinition = "timestamp")
    private LocalDateTime createdDate;

    @Column(name = "update_date", columnDefinition = "timestamp")
    private LocalDateTime updateDate;
}