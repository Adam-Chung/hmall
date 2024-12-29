package com.hmall.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.util.JwtTool;

import cn.hutool.core.text.AntPathMatcher;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor //自動生成構造函數 就不用去 Autowired
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authproperties;

    private final JwtTool jwtTool;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 獲取request
        ServerHttpRequest request = exchange.getRequest();

        // 2. 判斷是否需要做登陸攔截(有些路徑不需要判斷登入)
        if(isExclude(request.getPath().toString())){
            return chain.filter(exchange);
        }
        
        // 3. 獲取token
        List<String> headers = request.getHeaders().get("authorization");
        String token = null;
        if(headers != null && !headers.isEmpty()){
            token = headers.get(0);
        }

        // 4. 校驗並解析token
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (UnauthorizedException e) {
            // 攔截 設置響應狀態碼為401 (未登入)
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); // 終止請求(不會再往下跑其他filter等，直接傳回前端)
        }

        // 5. 傳遞用戶訊息
        // 將用戶訊息放到request header中
        String userInfo = userId.toString();
        ServerWebExchange swe = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo))
                .build();


        // 6. 放行
        return chain.filter(swe);
    }

    private boolean isExclude(String path) {
        for (String excludePath : authproperties.getExcludePaths()) {
            //excludePath 會是 /search/** 這種格式 ，所以用antPathMatcher去做匹配
            if(antPathMatcher.match(excludePath, path)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
