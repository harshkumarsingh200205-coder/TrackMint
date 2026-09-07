package com.trackmint.service;

import com.trackmint.model.User;
import com.trackmint.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService() {
        this(new UserRepository());
    }

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String name, String email, String password) {
        User user = new User(0, name, email, password);
        return userRepository.registerUser(user);
    }

    public User login(String email, String password) {
        return userRepository.loginUser(email, password);
    }
}