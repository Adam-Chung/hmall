package com.hmall.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyglobalFilter implements GlobalFilter, Ordered { //for Demo

    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // TODO 模擬登陸校驗邏輯
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        // System.out.println("headers: " + headers);
        return chain.filter(exchange); // 放行，調用下一個過濾器
    }

    @Override
    public int getOrder() {
        //因為要比 NettyRoutingFilter(最後打去其他微服務的filter) 早執行，所以設定為 -1
        return 0;
    }

}
