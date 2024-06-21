package com.fdu.mockinterview.controller;

import com.fdu.mockinterview.auth.JwtHelper;
import com.fdu.mockinterview.auth.LoginResponse;
import com.fdu.mockinterview.common.Result;
import com.fdu.mockinterview.common.ResultBuilder;
import com.fdu.mockinterview.entity.User;
import com.fdu.mockinterview.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "User management APIs")
public class UserController {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    @Resource
    private UserService userService;

    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager) {
        this.passwordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

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
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPasswd()));
        String token = JwtHelper.generateToken(user.getUserName());

        return ResponseEntity.ok(new LoginResponse(user.getUserName(), token));
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
