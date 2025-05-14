package com.example.storage_manager.service;

import com.example.storage_manager.entity.User;
import com.example.storage_manager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // Verifica se o email já está registrado (opcional, mas recomendado)
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email já registrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Define os roles como uma lista, se estiver nulo
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("ROLE_USER")); // Corrige o erro usando List.of
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User login(String email, String password) {
        User user = findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
