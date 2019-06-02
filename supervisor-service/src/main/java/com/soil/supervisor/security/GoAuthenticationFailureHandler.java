package com.soil.supervisor.security;

import com.alibaba.fastjson.JSON;
import com.soil.supervisor.enums.ErrorCode;
import com.soil.supervisor.response.ApiResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
