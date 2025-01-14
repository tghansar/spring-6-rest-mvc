package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2025/01/13, Mon, 12:25
 **/
public interface BeerCSVService {

    List<BeerCSVRecord> convertCsv(File csvFile);
}
