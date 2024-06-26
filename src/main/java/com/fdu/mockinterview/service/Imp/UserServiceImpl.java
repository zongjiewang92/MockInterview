package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.User;
import com.fdu.mockinterview.mapper.UserMapper;
import com.fdu.mockinterview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
    @Override
    public Integer countUserByUserName(String userName) {
        return userMapper.countByUserName(userName);
    }

    @Override
    public ResponseEntity<Result<User>> createUser(User user) {

        Integer count = userMapper.countByUserName(user.getUserName());
        if (count>0){
            return ResponseEntity.ok(ResultBuilder.error("UserName already exist.", null));
        }
        // use BCryptPasswordEncoder to encode the password, salt and hash the password
        String encodedPassword = passwordEncoder.encode(user.getPasswd());
        user.setPasswd(encodedPassword);
        userMapper.insert(user);
        return ResponseEntity.ok(ResultBuilder.success(userMapper.findById(user.getId())));
    }

    @Override
    public User updateUser(User user) {
        // process for update password
        if(user.getPasswd() != null && !user.getPasswd().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getPasswd());
            user.setPasswd(encodedPassword);
        }
        userMapper.updateUser(user);
        return userMapper.findById(user.getId());
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
    }
}
