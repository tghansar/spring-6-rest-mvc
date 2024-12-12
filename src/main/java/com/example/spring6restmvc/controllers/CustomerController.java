package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.model.CustomerDto;
import com.example.spring6restmvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/03/11, Mon, 11:58
 **/

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CustomerDto customerDto) {

        CustomerDto savedCustomerDto = customerService.saveCustomer(customerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customerDto" + savedCustomerDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}") //<- path variable
    public CustomerDto getCustomerById(@PathVariable("customerId") UUID customerId) {

        return customerService.getCustomerById(customerId);
    }

    @GetMapping
    public List<CustomerDto> getCustomers() {

        return customerService.listCustomers();
    }

    @PutMapping("/{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto) {

        customerService.updateCustomerById(customerId, customerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId) {

        customerService.deleteCustomerById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patchById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto) {

        customerService.patchCustomerById(customerId, customerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
