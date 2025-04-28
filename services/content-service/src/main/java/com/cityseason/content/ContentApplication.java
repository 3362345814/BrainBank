package com.cityseason.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.cityseason.content.mapper")
@ComponentScan(basePackages = {"com.cityseason.content", "com.cityseason.common"})
@EnableFeignClients(basePackages = "com.cityseason.api.client")
public class ContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
