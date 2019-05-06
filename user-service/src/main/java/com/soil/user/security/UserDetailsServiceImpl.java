package com.soil.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import com.soil.user.bean.User;
import com.soil.user.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByAc(username);
        if (user == null) {
            throw new UsernameNotFoundException("未找到用户");
        }
        else {
            HashSet<SimpleGrantedAuthority> set = new HashSet<>();
            set.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new MyUserDetails(user.getAccount(),user.getPassword(),set);
        }
    }


}
