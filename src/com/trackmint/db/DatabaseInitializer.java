package com.trackmint.db;

import com.trackmint.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                );
                """;

        String createExpensesTable = """
                CREATE TABLE IF NOT EXISTS expenses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    title TEXT NOT NULL,
                    amount REAL NOT NULL,
                    category TEXT NOT NULL,
                    payment_mode TEXT NOT NULL,
                    expense_date TEXT NOT NULL,
                    notes TEXT,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
                """;

        String createBudgetsTable = """
                CREATE TABLE IF NOT EXISTS budgets (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    month TEXT NOT NULL,
                    total_budget REAL NOT NULL,
                    UNIQUE(user_id, month),
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
                """;

        String createBudgetIndex = """
                CREATE UNIQUE INDEX IF NOT EXISTS idx_budgets_user_month ON budgets(user_id, month);
                """;

        String createExpenseIndex = """
                CREATE INDEX IF NOT EXISTS idx_expenses_user_date ON expenses(user_id, expense_date);
                """;

        String insertDefaultUser = """
                INSERT OR IGNORE INTO users (id, name, email, password)
                VALUES (1, 'Default User', 'default@trackmint.com', ?);
                """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA journal_mode = WAL;");
            stmt.execute(createUsersTable);
            stmt.execute(createExpensesTable);
            stmt.execute(createBudgetsTable);
            stmt.execute(createBudgetIndex);
            stmt.execute(createExpenseIndex);

            try (PreparedStatement pstmt = conn.prepareStatement(insertDefaultUser)) {
                pstmt.setString(1, PasswordUtil.hashPassword("1234"));
                pstmt.executeUpdate();
            }

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }
}