package com.example.spring6restmvc.services;

import com.example.spring6restmvc.exception.NotFoundException;
import com.example.spring6restmvc.mappers.BeerMapper;
import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/11/04, Mon, 14:38
 **/

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public List<BeerDto> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
//        return beerRepository.findById(id)
//                .map(beerMapper::beerToBeerDto);
        return Optional.ofNullable(
                beerMapper.beerToBeerDto(beerRepository.findById(id)
                        .orElse(null)));
    }


    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public Optional<BeerDto> updateBeerById(UUID beerId, BeerDto beerDto) {
//        beerRepository.findById(beerId)
//                .ifPresentOrElse(savedBeer -> {
//                    Beer beer = beerMapper.beerDtoToBeer(beerDto);
//                    beer.setId(beerId);
//                    beerRepository.save(beer);
//                    }, () -> {throw new NotFoundException();}
//                );
//        beerRepository.findById(beerId)
//                .ifPresentOrElse(savedBeer -> {
//                        Beer beer = beerMapper.beerDtoToBeer(beerDto);
//                        beer.setId(beerId);
//                        beerRepository.save(beer);
//                    }, () -> {throw new NotFoundException();}
//                );
        return beerRepository.findById(beerId)
                .map(foundBeer -> {
                    foundBeer.setBeerName(beerDto.getBeerName());
                    foundBeer.setBeerStyle(beerDto.getBeerStyle());
                    foundBeer.setUpc(beerDto.getUpc());
                    foundBeer.setPrice(beerDto.getPrice());
                    return Optional.of(
                            beerMapper.beerToBeerDto(
                                    beerRepository.save(foundBeer)));
                })
                .orElseThrow(NotFoundException::new);
    }


    @Override
    public Boolean deleteBeerById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }


    @Override
    public Optional<BeerDto> patchBeerById(UUID beerId, BeerDto beer) {
        AtomicReference<Optional<BeerDto>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beer.getBeerName())){
                foundBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null){
                foundBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (StringUtils.hasText(beer.getUpc())){
                foundBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                foundBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
