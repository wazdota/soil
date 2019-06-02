package com.soil.admin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soil.admin.bean.Admin;
import com.soil.admin.enums.ErrorCode;
import com.soil.admin.mapper.AdminMapper;
import com.soil.admin.response.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private AdminMapper adminMapper;

    @Autowired
    public AdminService (AdminMapper adminMapper){
        this.adminMapper = adminMapper;
    }

    public ApiResult register(Admin admin){
        if(adminMapper.findByAc(admin.getAccount()) != null){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(admin.getPassword()));
        try{
            adminMapper.insert(admin);
            return new ApiResult(ErrorCode.CREATED);
        }catch (DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public Admin findById(int id){
        try{
            return adminMapper.findById(id);
        }catch (DataAccessException e){
            return null;
        }
    }

    public ApiResult updateAdmin(Admin admin){
        if(admin.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            admin.setPassword(encoder.encode(admin.getPassword()));
            try {
                adminMapper.updatePassword(admin);
            } catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        if(admin.getName() != null) {
            try {
                adminMapper.updateName(admin);
            } catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        return new ApiResult(ErrorCode.CREATED);
    }

    public ApiResult selectAllAdmins(int pageNo, int pageSize, Integer id, String account, String name){
        if(id != null){
            try{
                PageHelper.startPage(pageNo, pageSize);
                List<Admin> list = adminMapper.selectById(id);
                PageInfo<Admin> page = new PageInfo<>(list);
                return new ApiResult<>(ErrorCode.OK,page);
            }catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        else {
            try{
                PageHelper.startPage(pageNo,pageSize);
                List<Admin> list = adminMapper.selectAdmins(account,name);
                PageInfo<Admin> page = new PageInfo<>(list);
                return new ApiResult<>(ErrorCode.OK,page);
            }catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
    }

    public ApiResult deleteAdmin(int id){
        try{
            adminMapper.deleteById(id);
            return new ApiResult(ErrorCode.NO_CONTENT);
        }catch (DataAccessException e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }
}
