package com.hmall.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hmall.common.interceptors.UserInfoInterceptor;

@Configuration
@ConditionalOnClass(DispatcherServlet.class) // DispatcherServlet類(是spring mvc架構才有)存在時才會生效，所以hm-gateway不是spring mvc架構所以不會有，所以不會攔截，不然如果hm-gateway也導入此類會因為WebMvcConfigurer而報錯
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInfoInterceptor()); // 不寫路徑，表示所有路徑都攔截
    }

}
