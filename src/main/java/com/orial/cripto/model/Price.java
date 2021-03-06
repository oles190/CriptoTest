package com.orial.cripto.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Comparable<Price> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long createdAt;
    private Double lPrice;
    private String curr1;
    private String curr2;


    @Override
    public int compareTo(Price o) {
        return this.lPrice.compareTo(o.getLPrice());
    }

}
