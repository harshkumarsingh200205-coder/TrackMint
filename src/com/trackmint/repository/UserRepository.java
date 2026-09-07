package com.trackmint.repository;

import com.trackmint.db.DBConnection;
import com.trackmint.model.User;
import com.trackmint.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, PasswordUtil.hashPassword(user.getPassword()));

            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
            return true;

        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE")) {
                System.out.println("Email is already registered. Please login instead.");
            } else {
                System.out.println("Error registering user: " + e.getMessage());
            }
            return false;
        }
    }

    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User authenticatedUser = null;
        boolean needsUpgrade = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    int userId = rs.getInt("id");

                    if (PasswordUtil.verifyPassword(password, storedPassword)) {
                        authenticatedUser = new User(
                                userId,
                                rs.getString("name"),
                                rs.getString("email"),
                                storedPassword
                        );

                        if (!PasswordUtil.isHashed(storedPassword)) {
                            needsUpgrade = true;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }

        if (authenticatedUser != null && needsUpgrade) {
            String newHash = PasswordUtil.hashPassword(password);
            updatePassword(authenticatedUser.getId(), newHash);
            authenticatedUser.setPassword(newHash);
        }

        return authenticatedUser;
    }

    public boolean updatePassword(int userId, String newHashedPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newHashedPassword);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating password hash: " + e.getMessage());
            return false;
        }
    }
}