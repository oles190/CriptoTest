package com.orial.cripto.repository;

import com.orial.cripto.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findAllByCryptocurrency(String cryptocurrency);

}
