package com.soil.admin.security;

import com.alibaba.fastjson.JSON;
import com.soil.admin.bean.Admin;
import com.soil.admin.enums.ErrorCode;
import com.soil.admin.mapper.AdminMapper;
import com.soil.admin.response.ApiResult;
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
     private AdminMapper adminMapper;

    @Autowired
    public GoAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil, AdminMapper adminMapper){
        this.jwtTokenUtil = jwtTokenUtil;
        this.adminMapper= adminMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Admin admin = adminMapper.findByAc(userDetails.getUsername());
        String jwtToken = jwtTokenUtil.generateToken(admin);
        ApiResult result = new ApiResult<>(ErrorCode.OK,jwtToken);
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
