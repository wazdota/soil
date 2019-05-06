package com.soil.user.security;

import com.alibaba.fastjson.JSON;
import com.soil.user.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soil.user.mapper.UserMapper;
import com.soil.user.response.ApiResult;
import com.soil.user.enums.ErrorCode;

@Component
public class GoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

     private JwtTokenUtil jwtTokenUtil;
     private UserMapper userMapper;

    @Autowired
    public GoAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil, UserMapper userMapper){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException, ServletException{
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userMapper.findByAc(userDetails.getUsername());
        String jwtToken = jwtTokenUtil.generateToken(user);
        ApiResult result = new ApiResult<>(ErrorCode.OK,jwtToken);
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
