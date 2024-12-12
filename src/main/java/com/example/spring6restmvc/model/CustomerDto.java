package com.example.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2023/09/21, Thu, 09:59
 **/

@Builder
@Data
public class CustomerDto {
    private UUID id;
    private Integer version;
    private String name;
    private String surname;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
