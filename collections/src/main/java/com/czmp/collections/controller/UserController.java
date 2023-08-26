package com.czmp.collections.controller;

import com.czmp.collections.dto.UserLoginDTO;
import com.czmp.collections.dto.UserRegisterDTO;
import com.czmp.collections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(userService.login(userLoginDTO));
    }
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userService.register(userRegisterDTO));
    }
}
