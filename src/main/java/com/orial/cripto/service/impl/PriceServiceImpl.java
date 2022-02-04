package com.orial.cripto.service.impl;

import com.orial.cripto.comperator.PriceComparator;
import com.orial.cripto.model.Price;
import com.orial.cripto.repository.PriceRepository;
import com.orial.cripto.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {


    File file = new File("/Users/olesdoskuc/Desktop/orial/src/main/java/com/orial/cripto/output.txt");

    private final PriceRepository priceRepository;
    private final PriceComparator comparator;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, PriceComparator comparator) {
        this.priceRepository = priceRepository;
        this.comparator = comparator;
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

