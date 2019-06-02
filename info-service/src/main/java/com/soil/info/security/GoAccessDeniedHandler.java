package com.soil.info.security;

import com.alibaba.fastjson.JSON;
import com.soil.info.enums.ErrorCode;
import com.soil.info.response.ApiResult;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GoAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        ApiResult result = new ApiResult(ErrorCode.Forbidden);
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
