package com.orial.cripto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OrialApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrialApplication.class, args);
    }

}
