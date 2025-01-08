package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.exception.NotFoundException;
import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2023/09/21, Thu, 10:29
 **/

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/beer") //<- path parameter
public class BeerController {

    private final BeerService beerService;

    public static String BEER_PATH = "/api/v1/beer";

//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity createBeer(@Validated @RequestBody BeerDto beerDto) {

        BeerDto savedBeerDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", String.format("%s/%s", BEER_PATH, savedBeerDto.getId().toString()));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{beerId}") //<- path variable
    public BeerDto getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("BeerController.getBeerById() was called");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public List<BeerDto> listBeers() {

        return beerService.listBeers();
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {

        beerService.updateBeerById(beerId, beerDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {

        if (!beerService.deleteBeerById(beerId)) throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{beerId}")
    public ResponseEntity patchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {

        beerService.patchBeerById(beerId, beerDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
