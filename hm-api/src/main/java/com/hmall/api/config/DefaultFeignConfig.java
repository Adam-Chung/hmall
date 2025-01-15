package com.hmall.api.config;

import org.springframework.context.annotation.Bean;

import com.hmall.api.client.fallback.ItemClientFallbackFactory;
import com.hmall.common.utils.UserContext;

import feign.Logger;
import feign.RequestInterceptor;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; //完整日誌輸出
    }

    @Bean
    public RequestInterceptor userInfoRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(feign.RequestTemplate requestTemplate) {
                // 1. 獲取用戶訊息
                Long userId = UserContext.getUser();
                requestTemplate.header("user-info", userId == null ? "" : userId.toString());
                // 2. 將用戶訊息放到request header中
            }
        };
    }

    @Bean
    public ItemClientFallbackFactory itemClientFallbackFactory(){
        return new ItemClientFallbackFactory();
    }

}
