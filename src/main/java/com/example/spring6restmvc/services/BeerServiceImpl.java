package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.enums.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2023/09/21, Thu, 10:17
 **/

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDto> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDto beerDto1 = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDto beerDto2 = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("23456")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDto beerDto3 = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("34567")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        this.beerMap.put(beerDto1.getId(), beerDto1);
        this.beerMap.put(beerDto2.getId(), beerDto2);
        this.beerMap.put(beerDto3.getId(), beerDto3);
    }

    @Override
    public List<BeerDto> listBeers(){
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {

        return Optional.ofNullable(beerMap.get(id));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        BeerDto savedBeerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName(beerDto.getBeerName())
                .beerStyle(beerDto.getBeerStyle())
                .upc(beerDto.getUpc())
                .price(beerDto.getPrice())
                .quantityOnHand(beerDto.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(savedBeerDto.getId(), savedBeerDto);

        return savedBeerDto;
    }

    @Override
    public Optional<BeerDto> updateBeerById(UUID beerId, BeerDto beerDto) {

        BeerDto existingBeerDto = beerMap.get(beerId);

        existingBeerDto.setBeerName(beerDto.getBeerName());
        existingBeerDto.setPrice(beerDto.getPrice());
        existingBeerDto.setUpc(beerDto.getUpc());
        existingBeerDto.setQuantityOnHand(beerDto.getQuantityOnHand());

        beerMap.put(existingBeerDto.getId(), existingBeerDto);
        return Optional.of(existingBeerDto);
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public Optional<BeerDto> patchBeerById(UUID beerId, BeerDto beerDto) {

        BeerDto existingBeerDto = beerMap.get(beerId);

        if (existingBeerDto == null) return Optional.empty();

        if (StringUtils.hasText(beerDto.getBeerName())) {
            existingBeerDto.setBeerName(beerDto.getBeerName());
        }

        if (beerDto.getBeerStyle() != null) {
            existingBeerDto.setBeerStyle(beerDto.getBeerStyle());
        }

        if (beerDto.getPrice() != null) {
            existingBeerDto.setPrice(beerDto.getPrice());
        }

        if (beerDto.getQuantityOnHand() != null) {
            existingBeerDto.setQuantityOnHand(beerDto.getQuantityOnHand());
        }

        if (StringUtils.hasText(beerDto.getUpc())) {
            existingBeerDto.setUpc(beerDto.getUpc());
        }
        return Optional.empty();
    }
}
