package com.example.spring6restmvc.services;

import com.example.spring6restmvc.exception.NotFoundException;
import com.example.spring6restmvc.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/03/11, Mon, 13:48
 **/

@Service
//@Primary
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    HashMap<UUID, CustomerDto> customerMap;

    public CustomerServiceImpl () {

        this.customerMap = new HashMap<>();

        CustomerDto customerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("John")
                .surname("Wick")
                .build();

        CustomerDto customerDto1 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Bruce")
                .surname("Lee")
                .build();

        this.customerMap.put(customerDto.getId(), customerDto);
        this.customerMap.put(customerDto1.getId(), customerDto1);
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {

        CustomerDto saveCustomerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .build();

        this.customerMap.put(saveCustomerDto.getId(), saveCustomerDto);
        return saveCustomerDto;
    }

    @Override
    public List<CustomerDto> listCustomers() {

        List<CustomerDto> result = new ArrayList<>(customerMap.size());

        for (Map.Entry<UUID, CustomerDto> customerEntry: customerMap.entrySet()) {
            result.add(customerEntry.getValue());
        }

        return result;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDto customerDto) {

        CustomerDto existingCustomerDto = customerMap.get(customerId);
        existingCustomerDto.setName(customerDto.getName());
        existingCustomerDto.setSurname(customerDto.getSurname());

        customerMap.put(existingCustomerDto.getId(), existingCustomerDto);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {

        CustomerDto customerDto = customerMap.remove(customerId);
        return customerDto != null;
    }

    @Override
    public CustomerDto getCustomerById(UUID customerId) {

        CustomerDto customerDto = customerMap.get(customerId);
        if (customerDto == null) throw new NotFoundException();
        return customerDto;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDto customerDto) {

        CustomerDto existingCustomerDto = customerMap.get(customerId);

        if (existingCustomerDto == null) return;

        if (StringUtils.hasText(customerDto.getName())) {
            existingCustomerDto.setName(customerDto.getName());
        }

        if (StringUtils.hasText(customerDto.getSurname())) {
            existingCustomerDto.setSurname(customerDto.getSurname());
        }
    }
}
