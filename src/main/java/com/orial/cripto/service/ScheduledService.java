package com.orial.cripto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orial.cripto.entity.Price;
import com.orial.cripto.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {

    private final PriceRepository priceRepository;

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() throws JsonProcessingException {
        log.info("Start time {}", System.currentTimeMillis());
        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrlBTC = "https://cex.io/api/last_price/BTC/USD";
        String fooResourceUrlETH = "https://cex.io/api/last_price/ETH/USD";
        String fooResourceUrlXRP = "https://cex.io/api/last_price/XRP/USD";

        String responseBTC = restTemplate.getForEntity(fooResourceUrlBTC, String.class).getBody();
        String responseETH = restTemplate.getForEntity(fooResourceUrlETH, String.class).getBody();
        String responseXRP = restTemplate.getForEntity(fooResourceUrlXRP, String.class).getBody();

        Price priceBTC = intoObject(responseBTC);
        Price priceETH = intoObject(responseETH);
        Price priceXRP = intoObject(responseXRP);

        long currentTimeMillis = System.currentTimeMillis();
        priceBTC.setCreatedAt(currentTimeMillis);
        priceETH.setCreatedAt(currentTimeMillis);
        priceXRP.setCreatedAt(currentTimeMillis);

        priceRepository.saveAll(Arrays.asList(priceBTC, priceETH, priceXRP));

        log.info("Saved all cryptocurrencies to db. Time {}", System.currentTimeMillis());
    }

    public Price intoObject(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(response, Price.class);
    }
}