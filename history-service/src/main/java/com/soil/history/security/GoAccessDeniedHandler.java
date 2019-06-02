package com.soil.history.security;

import com.alibaba.fastjson.JSON;
import com.soil.history.enums.ErrorCode;
import com.soil.history.response.ApiResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
