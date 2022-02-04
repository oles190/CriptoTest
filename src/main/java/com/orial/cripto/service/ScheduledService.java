package com.orial.cripto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orial.cripto.model.Price;
import com.orial.cripto.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ScheduledService {

    private final PriceRepository priceRepository;


    @Autowired
    public ScheduledService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;

    }

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() throws JsonProcessingException {
        System.out.println("Time " + System.currentTimeMillis());
        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrlBTC = "https://cex.io/api/last_price/BTC/USD";
        String fooResourceUrlETH = "https://cex.io/api/last_price/ETH/USD";
        String fooResourceUrlXRP = "https://cex.io/api/last_price/XRP/USD";

        String responseBTC = restTemplate.getForEntity(fooResourceUrlBTC, String.class).getBody();
        String responseETH = restTemplate.getForEntity(fooResourceUrlETH, String.class).getBody();
        String responseXRP = restTemplate.getForEntity(fooResourceUrlXRP, String.class).getBody();

        Price price1 = intoObject(responseBTC);
        Price price2 = intoObject(responseETH);
        Price price3 = intoObject(responseXRP);

        price1.setCreatedAt(System.currentTimeMillis());
        price2.setCreatedAt(System.currentTimeMillis());
        price3.setCreatedAt(System.currentTimeMillis());

        List<Price> lists = new ArrayList<>(Arrays.asList(price1, price2, price3));
        priceRepository.saveAll(lists);
    }

    public Price intoObject(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, Price.class);
    }


}