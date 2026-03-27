package com.trackmint.repository;

import com.trackmint.db.DBConnection;
import com.trackmint.model.Category;
import com.trackmint.model.Expense;
import com.trackmint.model.PaymentMode;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (user_id, title, amount, category, payment_mode, expense_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, expense.getUserId());
            pstmt.setString(2, expense.getTitle());
            pstmt.setDouble(3, expense.getAmount());
            pstmt.setString(4, expense.getCategory().name());
            pstmt.setString(5, expense.getPaymentMode().name());
            pstmt.setString(6, expense.getExpenseDate().toString());
            pstmt.setString(7, expense.getNotes());

            pstmt.executeUpdate();
            System.out.println("Expense added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        Category.valueOf(rs.getString("category")),
                        PaymentMode.valueOf(rs.getString("payment_mode")),
                        LocalDate.parse(rs.getString("expense_date")),
                        rs.getString("notes")
                );
                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching expenses: " + e.getMessage());
        }

        return expenses;
    }
    public List<Expense> getAllExpensesByUser(int userId) {
    List<Expense> expenses = new ArrayList<>();
    String sql = "SELECT * FROM expenses WHERE user_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Expense expense = new Expense(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getDouble("amount"),
                    Category.valueOf(rs.getString("category")),
                    PaymentMode.valueOf(rs.getString("payment_mode")),
                    LocalDate.parse(rs.getString("expense_date")),
                    rs.getString("notes")
            );
            expenses.add(expense);
        }

    } catch (SQLException e) {
        System.out.println("Error fetching user expenses: " + e.getMessage());
    }

    return expenses;
}

    public void updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET title = ?, amount = ?, category = ?, payment_mode = ?, expense_date = ?, notes = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, expense.getTitle());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getCategory().name());
            pstmt.setString(4, expense.getPaymentMode().name());
            pstmt.setString(5, expense.getExpenseDate().toString());
            pstmt.setString(6, expense.getNotes());
            pstmt.setInt(7, expense.getId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Expense updated successfully.");
            } else {
                System.out.println("Expense ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating expense: " + e.getMessage());
        }
    }

    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Expense deleted successfully.");
            } else {
                System.out.println("Expense ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
        }
    }
}