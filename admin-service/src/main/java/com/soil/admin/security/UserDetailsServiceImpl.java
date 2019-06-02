package com.soil.admin.security;

import com.soil.admin.bean.Admin;
import com.soil.admin.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AdminMapper adminMapper;

    @Autowired
    public UserDetailsServiceImpl(AdminMapper adminMapper){
        this.adminMapper = adminMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.findByAc(username);
        if (admin == null) {
            throw new UsernameNotFoundException("未找到用户");
        }
        else {
            HashSet<SimpleGrantedAuthority> set = new HashSet<>();
            set.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new MyUserDetails(admin.getAccount(),admin.getPassword(),set);
        }
    }


}
