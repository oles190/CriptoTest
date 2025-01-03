package com.orial.cripto;

import com.orial.cripto.entity.Price;

public class TestingReflection {


    public static void main(String[] args) {


        Price price = new Price();
        Class<? extends Price> aClass = price.getClass();
        Class<Price> priceClass = Price.class;


    }

}
