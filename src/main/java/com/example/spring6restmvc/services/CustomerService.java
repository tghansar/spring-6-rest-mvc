package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.CustomerDto;

import java.util.List;
import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/03/11, Mon, 13:46
 **/
public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto);

    List<CustomerDto> listCustomers();

    void updateCustomerById(UUID customerId, CustomerDto customerDto);

    Boolean deleteCustomerById(UUID customerId);

    CustomerDto getCustomerById(UUID customerId);

    void patchCustomerById(java.util.UUID customerId, CustomerDto customerDto);
}
