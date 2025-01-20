package com.example.spring6restmvc.repositories;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.enums.BeerStyle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/10/31, Thu, 16:24
 **/
public interface BeerRepository extends JpaRepository <Beer, UUID> {

    Page<Beer> findBeerByBeerNameLikeIgnoreCase(@NotNull String beerName, Pageable pageable);

    Page<Beer> findAllByBeerStyle(@NotNull BeerStyle beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(@NotNull String beerName, @NotNull BeerStyle beerStyle, Pageable pageable);
}
