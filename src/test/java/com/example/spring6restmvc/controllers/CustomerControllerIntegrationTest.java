package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.exception.NotFoundException;
import com.example.spring6restmvc.repositories.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/11/13, Wed, 17:29
 **/

@SpringBootTest
public class CustomerControllerIntegrationTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testCustomerIdNotFound() {
        org.junit.jupiter.api.Assertions
                .assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testListCustomers() {
        var customers = customerController.getCustomers();
        Assertions.assertThat(customers).isNotNull();
    }

    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        var customers = customerController.getCustomers();
        Assertions.assertThat(customers.size()).isEqualTo(0);
    }
}
