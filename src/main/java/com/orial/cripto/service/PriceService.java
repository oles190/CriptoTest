package com.orial.cripto.service;

import com.orial.cripto.model.Price;

import java.util.List;

public interface PriceService {

    List<Price> findAllByCurr1(String curr1);

    Price minPriceByCurr1(String curr1);

    Price maxPriceBuCurr1(String curr1);

    void writeCsv();

}
