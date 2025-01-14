package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2025/01/13, Mon, 12:23
 **/
public class BeerCSVServiceImplTest {

    BeerCSVService beerCsvService = new BeerCSVServiceImpl();

    @Test
    void convertCsv() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> records = beerCsvService.convertCsv(file);

        System.out.println(records.size());

        assertThat(records.size()).isEqualTo(2410);

    }

}
