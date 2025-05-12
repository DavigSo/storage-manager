package com.example.storage_manager.controller;

import com.example.storage_manager.dto.UserLoginDTO;
import com.example.storage_manager.dto.UserRegisterDTO;
import com.example.storage_manager.entity.Product;
import com.example.storage_manager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterDTO dto) {
            authService.register(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginDTO dto) {
        String token = authService.login(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
