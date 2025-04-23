package com.cityseason.content.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.cityseason.log.client"})
public class FeignConfig {
}