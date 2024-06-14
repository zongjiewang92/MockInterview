package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.User;
import com.fdu.mockinterview.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "User management APIs")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<Result<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ResultBuilder.success(userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<User>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResultBuilder.success(userService.getUserById(id)));
    }

    @GetMapping("/getUserByUserName")
    public ResponseEntity<Result<User>> getUserByUserName(@RequestParam String userName) {
        return ResponseEntity.ok(ResultBuilder.success(userService.getUserByUserName(userName)));
    }

    @PostMapping("/createUser")
    public ResponseEntity<Result<User>> createUser(@RequestBody User user) {
        // use BCryptPasswordEncoder to encode the password, salt and hash the password
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPasswd());
        user.setPasswd(encodedPassword);

        return userService.createUser(user);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Result<User>> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(ResultBuilder.success(userService.updateUser(user)));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Result<Integer>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ResultBuilder.success(id));
    }
}
