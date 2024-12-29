package com.hmall.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.hmall.common.utils.UserContext;

public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.獲取登陸用戶訊息
        String userInfo = request.getHeader("user-info");

        // 2. 將用戶訊息存到ThreadLocal
        if(StrUtil.isNotBlank(userInfo)){
            UserContext.setUser(Long.valueOf(userInfo));
        }

        // 3. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 4. 清空ThreadLocal
        UserContext.removeUser();

    }

}
