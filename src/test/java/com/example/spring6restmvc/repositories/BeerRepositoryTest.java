package com.example.spring6restmvc.repositories;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.enums.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/10/31, Thu, 16:37
 **/

@DataJpaTest
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("234234234234")
                .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush(); // persist to the DB immediately to make it possible to do a repository test

        Assertions.assertThat(savedBeer).isNotNull();
        Assertions.assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerNameTooLong() {
        org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class, () -> {
            beerRepository.save(Beer.builder()
                    .beerName("The Legendary Barrel-Aged Imperial Stout of Timeless Hops")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("234234234234")
                    .price(new BigDecimal("11.99"))
                    .build());

            beerRepository.flush(); // persist to the DB immediately to make it possible to do a repository test
        });
    }
}
