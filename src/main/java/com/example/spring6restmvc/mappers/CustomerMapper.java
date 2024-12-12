package com.example.spring6restmvc.mappers;

import com.example.spring6restmvc.entities.Customer;
import com.example.spring6restmvc.model.CustomerDto;
import org.mapstruct.Mapper;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/11/04, Mon, 14:04
 **/

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto dto);

    CustomerDto customerToCustomerDto(Customer customer);
}
