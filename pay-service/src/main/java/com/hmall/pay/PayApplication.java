package com.hmall.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.hmall.api.client.ItemClient;
import com.hmall.api.client.TradeClient;
import com.hmall.api.client.UserClient;
import com.hmall.api.config.DefaultFeignConfig;

@MapperScan("com.hmall.pay.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ItemClient.class, UserClient.class, TradeClient.class}, defaultConfiguration = DefaultFeignConfig.class) //開啟Feign功能 & 聲明要使用的FeignClient
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}