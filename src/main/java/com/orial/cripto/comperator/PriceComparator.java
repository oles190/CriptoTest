package com.orial.cripto.comperator;

import com.orial.cripto.model.Price;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class PriceComparator implements Comparator<Price> {

    @Override
    public int compare(Price o1, Price o2) {
        return (o2.getLPrice().compareTo(o1.getLPrice()));
    }
}
