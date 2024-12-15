package com.hmall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.hmall.api.config.DefaultFeignConfig;

@MapperScan("com.hmall.user.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {}, defaultConfiguration = DefaultFeignConfig.class) //開啟Feign功能 & 聲明要使用的FeignClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}