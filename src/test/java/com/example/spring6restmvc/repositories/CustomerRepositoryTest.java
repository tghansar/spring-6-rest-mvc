package com.example.spring6restmvc.repositories;

import com.example.spring6restmvc.entities.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/10/31, Thu, 16:44
 **/

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        var savedCustomer = customerRepository.save(Customer.builder()
                .name("Johnny")
                .build());
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getName()).isNotNull();
    }
}
