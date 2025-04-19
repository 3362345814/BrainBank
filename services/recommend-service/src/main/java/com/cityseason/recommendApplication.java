package com.cityseason;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class recommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(recommendApplication.class, args);
    }
}
