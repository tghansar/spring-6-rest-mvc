package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2023/09/21, Thu, 10:16
 **/

public interface BeerService {
    List<BeerDto> listBeers();

    Optional<BeerDto> getBeerById(UUID id);

    BeerDto saveNewBeer(BeerDto beerDto);

    Optional<BeerDto> updateBeerById(UUID beerId, BeerDto beerDto);

    Boolean deleteBeerById(UUID beerId);

    Optional<BeerDto> patchBeerById(UUID beerId, BeerDto beerDto);
}
