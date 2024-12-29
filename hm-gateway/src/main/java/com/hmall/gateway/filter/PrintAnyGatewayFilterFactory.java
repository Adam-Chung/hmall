package com.hmall.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.hmall.gateway.filter.PrintAnyGatewayFilterFactory.Config;

import lombok.Data;
import reactor.core.publisher.Mono;

@Component
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {  //for Demo
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // System.out.println("PrintAnyGatewayFilterFactory: " + exchange.getRequest().getURI());
                // System.out.println("a: " + config.getA());
                // System.out.println("b: " + config.getB());
                // System.out.println("c: " + config.getC());
                return chain.filter(exchange);
            }
        }, 1);
    }

    @Data
    public static class Config{
        private String a;
        private String b;
        private String c;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("a", "b", "c");
    }

    public PrintAnyGatewayFilterFactory(){
        super(Config.class);
    }
}
