package com.hmall.trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.hmall.api.client.CartClient;
import com.hmall.api.client.ItemClient;
import com.hmall.api.config.DefaultFeignConfig;

@MapperScan("com.hmall.trade.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ItemClient.class, CartClient.class}, defaultConfiguration = DefaultFeignConfig.class) //開啟Feign功能 & 聲明要使用的FeignClient
public class TradeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}