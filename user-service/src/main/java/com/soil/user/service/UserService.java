package com.soil.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.soil.user.bean.User;
import com.soil.user.response.ApiResult;
import com.soil.user.mapper.UserMapper;
import com.soil.user.enums.ErrorCode;

import java.util.ArrayList;
import java.util.List;

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

    public ApiResult updateUser(User user){
        if(user.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            try {
                userMapper.updatePassword(user);
            } catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        if(user.getName() != null) {
            try {
                userMapper.updateName(user);
            } catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        if(user.getMax() != 0) {
            try{
                userMapper.updateMax(user);
            } catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        return new ApiResult(ErrorCode.CREATED);
    }

    public ApiResult updateCount(User user) {
        try {
            userMapper.updateCount(user);
            return new ApiResult(ErrorCode.CREATED);
        } catch (DataAccessException e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public User findById(int id) {
        try {
            return userMapper.findById(id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public ApiResult selectAllUsers(int pageNo, int pageSize, Integer id, String account, String name){
        if(id != null){
            try{
                PageHelper.startPage(pageNo, pageSize);
                List<User> list = userMapper.selectById(id);
                PageInfo<User> page = new PageInfo<>(list);
                return new ApiResult<>(ErrorCode.OK,page);
            }catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        else {
            try{
                PageHelper.startPage(pageNo,pageSize);
                List<User> list = userMapper.selectUsers(account,name);
                PageInfo<User> page = new PageInfo<>(list);
                return new ApiResult<>(ErrorCode.OK,page);
            }catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
    }

    public ApiResult deleteUser(int id){
        try {
            userMapper.deleteById(id);
            return new ApiResult(ErrorCode.NO_CONTENT);
        }catch (DataAccessException e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }
}
