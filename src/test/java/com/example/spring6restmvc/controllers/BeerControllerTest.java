package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.services.BeerService;
import com.example.spring6restmvc.services.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2023/09/21, Thu, 17:11
 **/

@WebMvcTest(BeerController.class) //<- Test Splice, replaces @Mock BeerController;, also only need to manage dependencies for BeerController
@Slf4j
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDto> beerArgumentCaptor;

    @BeforeEach
    void setup() {
        beerServiceImpl = new BeerServiceImpl();
    }


    @Test
    void testPatchBeer() throws Exception {

        BeerDto beerDto = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "new name");

        mockMvc.perform(patch(BeerController.BEER_PATH + "/" + beerDto.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beerDto.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void deleteBeer() throws Exception {

        BeerDto beer = beerServiceImpl.listBeers().get(0);

        given(beerService.deleteBeerById(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH + "/" + beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());

        verify(beerService).deleteBeerById(uuidArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }


    @Test
    void testUpdateBeer() throws Exception {

        BeerDto beerDto = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_PATH + "/" + beerDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)));

        verify(beerService).updateBeerById(any(UUID.class), any(BeerDto.class));
        // verifying that the service update method was called at least once. An argument captor is an alternative
    }

    @Test
    void testUpdateBeerBlankName() throws Exception {

        BeerDto beer = beerServiceImpl.listBeers().get(0);
        beer.setBeerName("");
        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH + "/" + beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testCreateNewBeer() throws Exception {

        BeerDto beerDto = beerServiceImpl.listBeers().get(0);
        beerDto.setId(null);
        beerDto.setVersion(null);

        given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {

        BeerDto beerDto = BeerDto.builder().build();

        given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(beerServiceImpl.listBeers().get(1));

        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(jsonPath("$.length()", is(6)))
                .andExpect(status().isBadRequest()).andReturn();

        log.debug(">>>");
        log.debug(mvcResult.getResponse().getContentAsString());
        log.debug("<<<");
    }

    @Test
    void testListBeers() throws Exception {

        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }


    @Test
    void testGetBeerById() throws Exception {

        BeerDto testBeerDto = beerServiceImpl.listBeers().get(0);

        // mocks the service method
        given(beerService.getBeerById(testBeerDto.getId())).willReturn(Optional.of(testBeerDto));

        // goes to the controller method and accepts the given input and sets expectations for the return
        mockMvc.perform(get(BeerController.BEER_PATH+ "/" + testBeerDto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.beerName", is(testBeerDto.getBeerName())));
    }
}