package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.exception.NotFoundException;
import com.example.spring6restmvc.mappers.BeerMapper;
import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.repositories.BeerRepository;
import com.example.spring6restmvc.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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

    @Test
    void testUpdateBeerBadVersion() throws Exception {
        Beer beer = beerRepository.findAll().getFirst();

        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        beerDto.setBeerName("Updated Name");

        MvcResult result = mockMvc.perform(put(String.format("%s/%s", BeerController.BEER_PATH, beer.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        beerDto.setBeerName("Updated Name 2");

        MvcResult result2 = mockMvc.perform(put(String.format("%s/%s", BeerController.BEER_PATH, beer.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent())
                .andReturn();

        System.out.println(result2.getResponse().getStatus());
    }
}
