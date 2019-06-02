package com.soil.supervisor.security;

import com.alibaba.fastjson.JSON;
import com.soil.supervisor.enums.ErrorCode;
import com.soil.supervisor.response.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

     private JwtTokenUtil jwtTokenUtil;

     @Autowired
     public GoAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil){
         this.jwtTokenUtil = jwtTokenUtil;
     }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        String jwtToken = jwtTokenUtil.generateToken();
        ApiResult result = new ApiResult<>(ErrorCode.OK,jwtToken);
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
