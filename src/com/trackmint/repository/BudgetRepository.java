package com.trackmint.repository;

import com.trackmint.db.DBConnection;
import com.trackmint.model.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetRepository {

    public void setBudget(Budget budget) {
        String upsertSql = """
                INSERT INTO budgets (user_id, month, total_budget)
                VALUES (?, ?, ?)
                ON CONFLICT(user_id, month) DO UPDATE SET total_budget = excluded.total_budget;
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(upsertSql)) {

            pstmt.setInt(1, budget.getUserId());
            pstmt.setString(2, budget.getMonth());
            pstmt.setDouble(3, budget.getTotalBudget());

            pstmt.executeUpdate();
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

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Budget(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("month"),
                            rs.getDouble("total_budget")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching budget: " + e.getMessage());
        }

        return null;
    }
}