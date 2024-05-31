package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserService {

    List<User> getAllUsers();
//
//    User getUserById(Long id);
//
//    User createUser(User user);
//
//    void updateUser(User user);
//
//    void deleteUser(Long id);
}
