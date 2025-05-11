package com.example.storage_manager.service;

import com.example.storage_manager.dto.UserLoginDTO;
import com.example.storage_manager.dto.UserRegisterDTO;
import com.example.storage_manager.entity.User;
import com.example.storage_manager.mapper.UserMapper;
import com.example.storage_manager.repository.UserRepository;
import com.example.storage_manager.util.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    public String login(UserLoginDTO dto){
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("Senha incorreta");
        }
        return jwtService.generateToken((user.getEmail()));
    }

    public void register(UserRegisterDTO dto){
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Email já registrado");

        }
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singletonList("USER"));
        userRepository.save(user);
    }
}
