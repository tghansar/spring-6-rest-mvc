package com.example.spring6restmvc.services;

import com.example.spring6restmvc.entities.Customer;
import com.example.spring6restmvc.exception.NotFoundException;
import com.example.spring6restmvc.mappers.CustomerMapper;
import com.example.spring6restmvc.model.CustomerDto;
import com.example.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/11/04, Mon, 14:39
 **/

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        return customerMapper.customerToCustomerDto(
                customerRepository.save(customerMapper.customerDtoToCustomer(customerDto)));
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDto customerDto) {
        customerRepository.findById(customerId)
                .ifPresent(savedCustomer -> {
                    Customer customer = customerMapper.customerDtoToCustomer(customerDto);
                    customer.setId(customerId);
                    customerRepository.save(customer);
        });
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }


    @Override
    public void patchCustomerById(UUID customerId, CustomerDto customerDto) {
        // can change the method to return an Optional, see instructor examples
        customerRepository.findById(customerId)
                .ifPresentOrElse(customer -> {
                    customer.setName(customerDto.getName());
                    customer.setSurname(customerDto.getSurname());
                    customer.setVersion(customerDto.getVersion());
                    customer.setUpdateDate(LocalDateTime.now());
                    customer.setId(customerId);
                    customerRepository.save(customer);
                }, () -> {throw new NotFoundException();});
    }
}
