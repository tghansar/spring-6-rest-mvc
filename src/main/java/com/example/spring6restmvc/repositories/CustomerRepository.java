package com.example.spring6restmvc.repositories;

import com.example.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/10/31, Thu, 16:25
 **/
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
