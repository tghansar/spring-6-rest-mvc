package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.model.CustomerDto;
import com.example.spring6restmvc.services.CustomerService;
import com.example.spring6restmvc.services.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/03/13, Wed, 11:05
 **/

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    /*
        Use Spring MockMVC and Mockito to test Create endpoint for CustomerDto
        Write test to test creation of customer
        Verify HTTP 201 is returned
        Verify Location Header is returned
    **/

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDto> customerArgumentCaptor;

    CustomerService customerServiceImpl;

    // Re-creates CustomerServiceImpl before each test which helps in avoiding problems
    @BeforeEach
    void setup() {
        customerServiceImpl = new CustomerServiceImpl();
    }


//    @Test
//    void testPatchCustomer() throws Exception {
//
//        CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);
//
//        Map<String, Object> customerMap = new HashMap<>();
//        customerMap.put("customerName", "John");
//
//        mockMvc.perform(patch("/api/v1/customer/" + customerDto.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(customerDto)))
//                .andExpect(status().isNoContent());
//
//        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
//
//        assertThat(customerDto.getId()).isEqualTo(uuidArgumentCaptor.getValue());
//        assertThat(customerMap.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getName());
//    }


    @Test
    void testDeleteCustomer() throws Exception {

        CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete("/api/v1/customer/" + customerDto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

//        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

        Assertions.assertThat(customerDto.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }


    @Test
    void testUpdateCustomer() throws Exception {

        CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put("/api/v1/customer/" + customerDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)));

        verify(customerService).updateCustomerById(any(UUID.class), any(CustomerDto.class));
    }


    @Test
    void testCreateCustomer() throws Exception {

        CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);
        customerDto.setId(null);
        customerDto.setVersion(null);

        given(customerService.saveCustomer(any(CustomerDto.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }



    @Test
    void testListCustomers() throws Exception {

        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }


    @Test
    void testGetCustomerById() throws Exception {

        CustomerDto testCustomerDto = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(testCustomerDto.getId())).willReturn(testCustomerDto);

        mockMvc.perform(get("/api/v1/customer/" + testCustomerDto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(testCustomerDto.getName())))
                .andExpect(jsonPath("$.surname", is(testCustomerDto.getSurname())));
    }
}
