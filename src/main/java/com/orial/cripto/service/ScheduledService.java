package com.orial.cripto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orial.cripto.comperable.PriceComparator;
import com.orial.cripto.model.Price;
import com.orial.cripto.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
public class ScheduledService {

    private final PriceRepository priceRepository;
    private final PriceComparator comparator;
    File file = new File("/Users/olesdoskuc/Desktop/orial/src/main/java/com/orial/cripto/output.txt");


    @Autowired
    public ScheduledService(PriceRepository priceRepository, PriceComparator comparator) {
        this.priceRepository = priceRepository;
        this.comparator = comparator;
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


    public List<Price> findAllByCurr1(String curr1) {
        List<Price> all = priceRepository.findAllByCurr1(curr1);
        if (all.isEmpty()) {
            throw new NullPointerException("Not found " + curr1);
        }
        return all;
    }

    public Price minPriceByCurr1(String curr1) {
        List<Price> all = priceRepository.findAllByCurr1(curr1);
        return all.stream().sorted().findFirst()
                .orElseThrow(() -> new NullPointerException("NOT FOUND " + curr1));
    }

    public Price maxPriceBuCurr1(String curr1) {
        List<Price> all = priceRepository.findAllByCurr1(curr1);
        all.sort(comparator);
        return all.stream().findFirst()
                .orElseThrow(() -> new NullPointerException("NOT FOUND " + curr1));
    }

    public List<String> informationForCsv(String curr) {
        Price min = minPriceByCurr1(curr);
        Price max = maxPriceBuCurr1(curr);
        String name = "Name is " + curr + "\n";
        String highest = "The highest price is " + max.getLPrice() + "\n";
        String lower = "The lowest price is " + min.getLPrice() + ";\n\n";
        return new ArrayList<>(Arrays.asList(name, highest, lower));
    }

    public void writeCsv() {
        List<String> list1 = informationForCsv("BTC");
        List<String> list2 = informationForCsv("ETH");
        List<String> list3 = informationForCsv("XRP");

        try {
            file.createNewFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write((list1.get(0) + list1.get(1) + list1.get(2)).getBytes());
            outputStream.write((list2.get(0) + list2.get(1) + list2.get(2)).getBytes());
            outputStream.write((list3.get(0) + list3.get(1) + list3.get(2)).getBytes());

        } catch (IOException e) {
            e.getMessage();
        }
    }


}