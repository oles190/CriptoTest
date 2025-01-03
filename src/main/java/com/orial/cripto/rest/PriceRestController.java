package com.orial.cripto.rest;


import com.orial.cripto.entity.Price;
import com.orial.cripto.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceRestController {

    private final PriceService priceService;

    @GetMapping("/all/{cryptocurrency}")
    public List<Price> getAllByCryptocurrency(@PathVariable String cryptocurrency) {
        return priceService.findAllByCryptocurrency(cryptocurrency);
    }


    @GetMapping("/cryptocurrencies/min/price")
    public Price minCryptocurrency(@RequestParam String cryptocurrency) {
        return priceService.minPriceByCryptocurrency(cryptocurrency);
    }

    @GetMapping("/cryptocurrencies/max/price")
    public Price maxCryptocurrency(@RequestParam String cryptocurrency) {
        return priceService.maxPriceByCryptocurrency(cryptocurrency);
    }

    @GetMapping("/csv")
    public void prepareCsv() throws IOException {
        priceService.writeCsv();
    }


}
