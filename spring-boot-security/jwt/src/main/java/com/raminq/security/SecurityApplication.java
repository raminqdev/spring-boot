package com.raminq.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {
    public static void main(String[] args) {
        //System.setProperty("spring.main.lazy-initialization", "true");
        SpringApplication.run(SecurityApplication.class, args);
    }

}
