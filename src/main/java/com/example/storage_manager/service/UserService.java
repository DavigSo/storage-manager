package com.example.storage_manager.service;

import com.example.storage_manager.entity.User;
import com.example.storage_manager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getRole() == null) {

            user.setRole("ROLE_USER");
        }
        return userRepository.save(user);

    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

   /** public User login(String email, String password) {
        User user = findByEmail(email);
        if(passwordEncoder.matches(password, user.getPassword())) {

            return user;

        } else {
            return null;
        }
    } **/
}
