package com.soil.user.mapper;

import com.soil.user.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapper")
public interface UserMapper {
    int deleteById(int id);

    int insert(User record);

    User findByAc(String account);

    int updateName(User record);

    int updateCount(User record);

    int updatePassword(User record);

    User findById(int id);

    List<User> selectById(int id);

    List<User> selectUsers(@Param("account") String account, @Param("name") String name);

    int updateMax(User record);
}
