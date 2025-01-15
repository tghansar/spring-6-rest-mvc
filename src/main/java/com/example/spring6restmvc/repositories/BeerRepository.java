package com.example.spring6restmvc.repositories;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.enums.BeerStyle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/10/31, Thu, 16:24
 **/
public interface BeerRepository extends JpaRepository <Beer, UUID> {

    List<Beer> findBeerByBeerNameLikeIgnoreCase(@NotNull String beerName);

    List<Beer> findAllByBeerStyle(@NotNull BeerStyle beerStyle);

    List<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(@NotNull String beerName, @NotNull BeerStyle beerStyle);
}
