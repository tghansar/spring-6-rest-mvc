package com.example.spring6restmvc.mappers;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.model.BeerDto;
import org.mapstruct.Mapper;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/11/04, Mon, 14:03
 **/

@Mapper(componentModel = "spring")
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDto dto);

    BeerDto beerToBeerDto(Beer beer);
}
