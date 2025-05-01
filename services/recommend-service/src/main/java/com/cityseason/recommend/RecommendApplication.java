package com.cityseason.recommend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.cityseason.api.client")
@SpringBootApplication
@MapperScan("com.cityseason.recommend.mapper")
@ComponentScan(basePackages = {"com.cityseason.common", "com.cityseason.recommend"})
public class RecommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommendApplication.class, args);
    }
}
