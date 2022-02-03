package com.orial.cripto.comperable;

import com.orial.cripto.model.Price;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class PriceComparator implements Comparator<Price> {

    @Override
    public int compare(Price o1, Price o2) {
        return (int) (o2.getLPrice() - o1.getLPrice());
    }
}
