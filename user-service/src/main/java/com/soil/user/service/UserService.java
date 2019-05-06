package com.soil.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.soil.user.bean.User;
import com.soil.user.response.ApiResult;
import com.soil.user.mapper.UserMapper;
import com.soil.user.enums.ErrorCode;

@Service
public class UserService {

    private UserMapper userMapper;

    @Autowired
    public UserService (UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public ApiResult register(User user){
        if (userMapper.findByAc(user.getAccount()) != null) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userMapper.insert(user);
        return new ApiResult(ErrorCode.CREATED);
    }

    public int updatePwd(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            userMapper.updatePassword(user);
            return 201;
        } catch (DataAccessException e) {
            return 400;
        }
    }

    public int updateName(User user) {
        try {
            userMapper.updateName(user);
            return 201;
        } catch (DataAccessException e) {
            return 400;
        }
    }

    public ApiResult updateCount(User user) {
        try {
            userMapper.updateCount(user);
            return new ApiResult(ErrorCode.CREATED);
        } catch (DataAccessException e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }
}
