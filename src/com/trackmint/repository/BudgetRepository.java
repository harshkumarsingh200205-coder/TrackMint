package com.trackmint.repository;

import com.trackmint.db.DBConnection;
import com.trackmint.model.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetRepository {

    public void setBudget(Budget budget) {
        String checkSql = "SELECT * FROM budgets WHERE user_id = ? AND month = ?";
        String insertSql = "INSERT INTO budgets (user_id, month, total_budget) VALUES (?, ?, ?)";
        String updateSql = "UPDATE budgets SET total_budget = ? WHERE user_id = ? AND month = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, budget.getUserId());
            checkStmt.setString(2, budget.getMonth());

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, budget.getTotalBudget());
                    updateStmt.setInt(2, budget.getUserId());
                    updateStmt.setString(3, budget.getMonth());
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, budget.getUserId());
                    insertStmt.setString(2, budget.getMonth());
                    insertStmt.setDouble(3, budget.getTotalBudget());
                    insertStmt.executeUpdate();
                }
            }

            System.out.println("Budget saved successfully.");

        } catch (SQLException e) {
            System.out.println("Error saving budget: " + e.getMessage());
        }
    }

    public Budget getBudgetByUserAndMonth(int userId, String month) {
        String sql = "SELECT * FROM budgets WHERE user_id = ? AND month = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, month);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Budget(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("month"),
                        rs.getDouble("total_budget")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching budget: " + e.getMessage());
        }

        return null;
    }
}