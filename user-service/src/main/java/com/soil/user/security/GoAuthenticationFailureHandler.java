package com.soil.user.security;

import com.alibaba.fastjson.JSON;
import com.soil.user.enums.ErrorCode;
import com.soil.user.response.ApiResult;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GoAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        ApiResult result = new ApiResult(ErrorCode.Unauthorized);
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
