package com.trackmint.repository;

import com.trackmint.db.DBConnection;
import com.trackmint.exception.DatabaseException;
import com.trackmint.model.Category;
import com.trackmint.model.Expense;
import com.trackmint.model.PaymentMode;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseRepository {

    public boolean addExpense(Expense expense) {
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

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to add expense", e);
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
            throw new DatabaseException("Failed to fetch all expenses", e);
        }

        return expenses;
    }

    public List<Expense> getAllExpensesByUser(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
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
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch expenses for user", e);
        }

        return expenses;
    }

    public Expense getExpenseByIdAndUser(int id, int userId) {
        String sql = "SELECT * FROM expenses WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Expense(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getDouble("amount"),
                            Category.valueOf(rs.getString("category")),
                            PaymentMode.valueOf(rs.getString("payment_mode")),
                            LocalDate.parse(rs.getString("expense_date")),
                            rs.getString("notes")
                    );
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch expense by ID", e);
        }

        return null;
    }

    public boolean updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET title = ?, amount = ?, category = ?, payment_mode = ?, expense_date = ?, notes = ? WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, expense.getTitle());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getCategory().name());
            pstmt.setString(4, expense.getPaymentMode().name());
            pstmt.setString(5, expense.getExpenseDate().toString());
            pstmt.setString(6, expense.getNotes());
            pstmt.setInt(7, expense.getId());
            pstmt.setInt(8, expense.getUserId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to update expense", e);
        }
    }

    public boolean deleteExpense(int id, int userId) {
        String sql = "DELETE FROM expenses WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete expense", e);
        }
    }

    public double getMonthlyTotal(int userId, String month) {
        String sql = "SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE user_id = ? AND strftime('%Y-%m', expense_date) = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to calculate monthly total", e);
        }

        return 0.0;
    }

    public Map<String, Double> getCategoryWiseTotal(int userId, String month) {
        Map<String, Double> categoryTotals = new HashMap<>();
        String sql = "SELECT category, SUM(amount) FROM expenses WHERE user_id = ? AND strftime('%Y-%m', expense_date) = ? GROUP BY category";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categoryTotals.put(rs.getString(1), rs.getDouble(2));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to calculate category totals", e);
        }

        return categoryTotals;
    }

    public String getTopCategory(int userId, String month) {
        String sql = "SELECT category FROM expenses WHERE user_id = ? AND strftime('%Y-%m', expense_date) = ? GROUP BY category ORDER BY SUM(amount) DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to find top category", e);
        }

        return "No expenses found";
    }
}