package com.orial.cripto.rest;


import com.orial.cripto.model.Price;
import com.orial.cripto.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceRestController {

    private final PriceService priceService;

    @Autowired
    public PriceRestController(PriceService priceService) {
        this.priceService = priceService;
    }


    @GetMapping("/all/{curr1}")
    public List<Price> getAllByCurr1(@PathVariable String curr1) {
        return priceService.findAllByCurr1(curr1);
    }


    @GetMapping("/cryptocurrencies/minprice")
    public Price min(@RequestParam String curr1) {
        return priceService.minPriceByCurr1(curr1);
    }

    @GetMapping("/cryptocurrencies/maxprice")
    public Price max(@RequestParam String curr1) {
        return priceService.maxPriceBuCurr1(curr1);
    }

    @GetMapping("/csv")
    public void testOut() {
        priceService.writeCsv();
    }


}
