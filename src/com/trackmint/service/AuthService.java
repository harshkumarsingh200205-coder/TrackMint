package com.trackmint.service;

import com.trackmint.model.User;
import com.trackmint.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository = new UserRepository();

    public void register(String name, String email, String password) {
        User user = new User(0, name, email, password);
        userRepository.registerUser(user);
    }

    public User login(String email, String password) {
        return userRepository.loginUser(email, password);
    }
}