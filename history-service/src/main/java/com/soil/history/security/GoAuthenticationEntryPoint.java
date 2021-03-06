package com.soil.history.security;

import com.alibaba.fastjson.JSON;
import com.soil.history.response.ApiResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GoAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException{
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        ApiResult result = new ApiResult(403,"用户尚未登陆");
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }

}
