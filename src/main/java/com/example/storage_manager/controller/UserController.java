package com.example.storage_manager.controller;

import com.example.storage_manager.dto.UserRegistrationDTO;
import com.example.storage_manager.entity.User;
import com.example.storage_manager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO dto){
        try{
            User user = User.builder()
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .build();
            User registerUser = userService.registerUser(user);
            return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
