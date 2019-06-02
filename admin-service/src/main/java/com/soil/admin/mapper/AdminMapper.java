package com.soil.admin.mapper;

import com.soil.admin.bean.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    Admin findByAc(String account);

    int deleteById(int id);

    int insert(Admin record);

    int updateName(Admin record);

    int updatePassword(Admin record);

    List<Admin> selectById(int id);

    List<Admin> selectAdmins(@Param("account") String account, @Param("name") String name);

    Admin findById(int id);
}
