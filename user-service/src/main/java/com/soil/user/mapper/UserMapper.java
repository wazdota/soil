package com.soil.user.mapper;

import com.soil.user.bean.User;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper {
    int deleteById(int id);

    int insert(User record);

    User findByAc(String account);

    int updateName(User record);

    int updateCount(User record);

    int updatePassword(User record);
}
