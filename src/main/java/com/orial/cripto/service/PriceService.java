package com.orial.cripto.service;

import com.orial.cripto.entity.Price;

import java.io.IOException;
import java.util.List;

public interface PriceService {

    List<Price> findAllByCryptocurrency(String cryptocurrency);

    Price minPriceByCryptocurrency(String cryptocurrency);

    Price maxPriceByCryptocurrency(String cryptocurrency);

    void writeCsv() throws IOException;

}
