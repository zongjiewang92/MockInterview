package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.entity.User;
import com.fdu.mockinterview.mapper.UserMapper;
import com.fdu.mockinterview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
//
//    @Override
//    public User getUserById(Long id) {
//        return userMapper.findById(id);
//    }
//
//    @Override
//    public User createUser(User user) {
//        userMapper.insert(user);
//        return user;
//    }
//
//    @Override
//    public void updateUser(User user) {
//
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//
//    }

//    @Override
//    public void updateUser(User user) {
//        userMapper.update(user);
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        userMapper.delete(id);
//    }
}
