package com.example.storage_manager.controller;

import com.example.storage_manager.dto.UserRegisterDTO;
import com.example.storage_manager.dto.UserResponseDTO;
import com.example.storage_manager.entity.User;
import com.example.storage_manager.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegisterDTO dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        User savedUser = userService.registerUser(user);
        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRoles(savedUser.getRoles());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
