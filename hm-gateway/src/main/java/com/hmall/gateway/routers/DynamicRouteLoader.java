package com.hmall.gateway.routers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.hmall.common.utils.CollUtils;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class DynamicRouteLoader {

    private final NacosConfigManager nacosConfigManager;
    private final RouteDefinitionWriter writer;

    private final String dataId = "gateway-routes.json"; //因為yaml格式不好解析，所以用JSON格式
    private final String group = "DEFAULT_GROUP";

    private final Set<String> routeIds = new HashSet<>();


    @PostConstruct // 在構造函數之後執行，但會在此類加載時執行
    public void initRouteConfigListener() throws NacosException{
        // 1.項目啟動時 先拉取一次配置，並且添加配置監聽器
        String configInfo = nacosConfigManager.getConfigService()
        .getConfigAndSignListener(dataId, group, 50000, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                // 2.監聽到配置變更，需要去更新路由表
                updateConfigInfo(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        // 3. 第一次讀取到配置，也需要更新道路由表
        updateConfigInfo(configInfo);
    }


    // 4.更新路由表
    public void updateConfigInfo(String configInfo) {
        
        log.debug("監聽到路由配置訊息 : {}", configInfo);
        // 1.解析配置訊息
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);
        
        // 2.更新前先清空舊路由
        // 2.1.清除舊路由
        for (String routeId : routeIds) {
            writer.delete(Mono.just(routeId)).subscribe();
        }
        routeIds.clear();

        // 2.2.判斷是否有新的路由要更新
        if (CollUtils.isEmpty(routeDefinitions)) {
            // 無新路由配置，直接結束
            return;
        }
        // 3.更新路由
        routeDefinitions.forEach(routeDefinition -> {
            // 3.1.更新路由
            writer.save(Mono.just(routeDefinition)).subscribe();
            // 3.2.記錄路由id，方便將來刪除
            routeIds.add(routeDefinition.getId());
        });
    }
}
