package com.fdu.mockinterview.service;

import com.fdu.mockinterview.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserService {

    List<User> getAllUsers();

    User getUserById(Integer id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Integer id);
}
