package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.auth.SecurityConfig;
import com.fdu.mockinterview.entity.User;
import com.fdu.mockinterview.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userMapper.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // set authorities, simply use a string to represent the role
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + (username.equals("admin") ? SecurityConfig.adminRole: SecurityConfig.userRole)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPasswd())
                .authorities(authorities)
                .build();
    }
}