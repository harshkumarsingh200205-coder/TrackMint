package com.trackmint.ui;

import com.trackmint.model.User;
import com.trackmint.service.AuthService;
import com.trackmint.util.InputUtil;

public class AuthMenu {
    private final AuthService authService = new AuthService();

    public User showAuthMenu() {
        while (true) {
            System.out.println("\n===== TrackMint Authentication =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> register();
                case 2 -> {
                    User user = login();
                    if (user != null) {
                        return user;
                    }
                }
                case 3 -> {
                    System.out.println("Exiting application.");
                    return null;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void register() {
        String name = InputUtil.getString("Enter your name: ");
        String email = InputUtil.getString("Enter your email: ");
        String password = InputUtil.getString("Enter your password: ");

        authService.register(name, email, password);
    }

    private User login() {
        String email = InputUtil.getString("Enter your email: ");
        String password = InputUtil.getString("Enter your password: ");

        User user = authService.login(email, password);

        if (user == null) {
            System.out.println("Invalid email or password.");
        } else {
            System.out.println("Login successful. Welcome, " + user.getName() + "!");
        }

        return user;
    }
}