package com.customs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomsApplication {

    public static void main(String[] args) {
        // Fast startup optimization
        System.setProperty("spring.main.lazy-initialization", "true");
        SpringApplication.run(CustomsApplication.class, args);
    }
}