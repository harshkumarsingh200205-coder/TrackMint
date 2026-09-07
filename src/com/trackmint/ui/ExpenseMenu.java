package com.trackmint.ui;

import com.trackmint.exception.DatabaseException;
import com.trackmint.exception.TrackMintException;
import com.trackmint.model.Budget;
import com.trackmint.model.Category;
import com.trackmint.model.Expense;
import com.trackmint.model.PaymentMode;
import com.trackmint.service.BudgetService;
import com.trackmint.service.ExpenseService;
import com.trackmint.util.CsvExporter;
import com.trackmint.util.FormatUtil;
import com.trackmint.util.InputUtil;
import com.trackmint.util.TableUtil;
import com.trackmint.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

public class ExpenseMenu {

    private final ExpenseService expenseService;
    private final BudgetService budgetService;
    private final int userId;

    public ExpenseMenu(int userId) {
        this(userId, new ExpenseService(), new BudgetService());
    }

    public ExpenseMenu(int userId, ExpenseService expenseService) {
        this(userId, expenseService, new BudgetService());
    }

    public ExpenseMenu(int userId, ExpenseService expenseService, BudgetService budgetService) {
        this.userId = userId;
        this.expenseService = expenseService;
        this.budgetService = budgetService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n===== TrackMint Expense Menu =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses (Table)");
            System.out.println("3. Filter Expenses by Date Range");
            System.out.println("4. Filter Expenses by Category");
            System.out.println("5. Update Expense");
            System.out.println("6. Delete Expense");
            System.out.println("7. Export Expenses to CSV");
            System.out.println("8. Back to Main Menu");

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewAllExpenses();
                case 3 -> filterByDateRange();
                case 4 -> filterByCategory();
                case 5 -> updateExpense();
                case 6 -> deleteExpense();
                case 7 -> exportToCsv();
                case 8 -> {
                    System.out.println("Returning to Main Menu.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addExpense() {
        String title;
        do {
            title = InputUtil.getString("Enter title: ");
            if (!ValidationUtil.isValidTitle(title)) {
                System.out.println("Title cannot be empty.");
            }
        } while (!ValidationUtil.isValidTitle(title));

        double amount;
        do {
            amount = InputUtil.getDouble("Enter amount: ");
            if (!ValidationUtil.isValidAmount(amount)) {
                System.out.println("Amount must be greater than 0.");
            }
        } while (!ValidationUtil.isValidAmount(amount));

        Category category = promptCategory();
        PaymentMode paymentMode = promptPaymentMode();
        LocalDate expenseDate = promptExpenseDate();
        String notes = InputUtil.getString("Enter notes: ");

        try {
            boolean success = expenseService.addExpense(userId, title, amount, category, paymentMode, expenseDate, notes);
            if (success) {
                System.out.println("Expense added successfully.");
                checkBudgetAlert(expenseDate);
            } else {
                System.out.println("Failed to add expense.");
            }
        } catch (DatabaseException e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    private void viewAllExpenses() {
        try {
            List<Expense> expenses = expenseService.getAllExpensesByUser(userId);
            FormatUtil.printSection("ALL EXPENSES");
            TableUtil.printExpenseTable(expenses);
        } catch (DatabaseException e) {
            System.out.println("Error fetching expenses: " + e.getMessage());
        }
    }

    private void filterByDateRange() {
        String startDate;
        do {
            startDate = InputUtil.getString("Enter start date (YYYY-MM-DD): ");
            if (!ValidationUtil.isValidDate(startDate)) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        } while (!ValidationUtil.isValidDate(startDate));

        String endDate;
        do {
            endDate = InputUtil.getString("Enter end date (YYYY-MM-DD): ");
            if (!ValidationUtil.isValidDate(endDate)) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        } while (!ValidationUtil.isValidDate(endDate));

        try {
            List<Expense> expenses = expenseService.getExpensesByDateRange(userId, startDate, endDate);
            FormatUtil.printSection("EXPENSES (" + startDate + " to " + endDate + ")");
            TableUtil.printExpenseTable(expenses);
        } catch (DatabaseException e) {
            System.out.println("Error filtering expenses: " + e.getMessage());
        }
    }

    private void filterByCategory() {
        Category category = promptCategory();
        try {
            List<Expense> expenses = expenseService.getExpensesByCategory(userId, category);
            FormatUtil.printSection("EXPENSES FOR CATEGORY: " + category);
            TableUtil.printExpenseTable(expenses);
        } catch (DatabaseException e) {
            System.out.println("Error filtering expenses: " + e.getMessage());
        }
    }

    private void updateExpense() {
        int id = InputUtil.getInt("Enter expense ID to update: ");
        try {
            Expense existing = expenseService.getExpenseByIdAndUser(id, userId);
            if (existing == null) {
                System.out.println("Expense with ID " + id + " not found or does not belong to you.");
                return;
            }

            System.out.println("Editing expense #" + id + " [" + existing.getTitle() + " - " + FormatUtil.formatCurrency(existing.getAmount()) + "]");

            String title;
            do {
                title = InputUtil.getString("Enter new title: ");
                if (!ValidationUtil.isValidTitle(title)) {
                    System.out.println("Title cannot be empty.");
                }
            } while (!ValidationUtil.isValidTitle(title));

            double amount;
            do {
                amount = InputUtil.getDouble("Enter new amount: ");
                if (!ValidationUtil.isValidAmount(amount)) {
                    System.out.println("Amount must be greater than 0.");
                }
            } while (!ValidationUtil.isValidAmount(amount));

            Category category = promptCategory();
            PaymentMode paymentMode = promptPaymentMode();
            LocalDate expenseDate = promptExpenseDate();
            String notes = InputUtil.getString("Enter new notes: ");

            boolean success = expenseService.updateExpense(id, userId, title, amount, category, paymentMode, expenseDate, notes);
            if (success) {
                System.out.println("Expense updated successfully.");
                checkBudgetAlert(expenseDate);
            } else {
                System.out.println("Failed to update expense.");
            }
        } catch (DatabaseException e) {
            System.out.println("Error updating expense: " + e.getMessage());
        }
    }

    private void deleteExpense() {
        int id = InputUtil.getInt("Enter expense ID to delete: ");
        try {
            Expense existing = expenseService.getExpenseByIdAndUser(id, userId);
            if (existing == null) {
                System.out.println("Expense with ID " + id + " not found or does not belong to you.");
                return;
            }

            String confirm = InputUtil.getString("Are you sure you want to delete '" + existing.getTitle() + "' (ID: " + id + ")? (y/N): ");
            if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                boolean success = expenseService.deleteExpense(id, userId);
                if (success) {
                    System.out.println("Expense deleted successfully.");
                } else {
                    System.out.println("Failed to delete expense.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (DatabaseException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
        }
    }

    private void exportToCsv() {
        try {
            List<Expense> expenses = expenseService.getAllExpensesByUser(userId);
            if (expenses.isEmpty()) {
                System.out.println("No expenses available to export.");
                return;
            }
            String path = CsvExporter.exportExpenses(userId, expenses);
            System.out.println("Expenses exported successfully (" + expenses.size() + " records).");
            System.out.println("Saved to: " + path);
        } catch (TrackMintException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private void checkBudgetAlert(LocalDate date) {
        String month = String.format("%04d-%02d", date.getYear(), date.getMonthValue());
        try {
            Budget budget = budgetService.getBudgetByUserAndMonth(userId, month);
            if (budget != null && budget.getTotalBudget() > 0) {
                double totalSpent = expenseService.getMonthlyTotal(userId, month);
                double limit = budget.getTotalBudget();
                double pct = (totalSpent / limit) * 100.0;

                if (totalSpent > limit) {
                    double over = totalSpent - limit;
                    System.out.println("🚨 Alert: You have exceeded your monthly budget of " +
                            FormatUtil.formatCurrency(limit) + " by " + FormatUtil.formatCurrency(over) +
                            " (Total Spent: " + FormatUtil.formatCurrency(totalSpent) + ")!");
                } else if (pct >= 80.0) {
                    System.out.printf("⚠️  Warning: You have spent %.1f%% of your monthly budget (%s / %s).%n",
                            pct, FormatUtil.formatCurrency(totalSpent), FormatUtil.formatCurrency(limit));
                }
            }
        } catch (Exception ignored) {
        }
    }

    private Category promptCategory() {
        Category[] categories = Category.values();
        while (true) {
            System.out.println("Select Category:");
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("  %d. %s%n", (i + 1), categories[i]);
            }
            int choice = InputUtil.getInt("Enter category choice (1-" + categories.length + "): ");
            if (choice >= 1 && choice <= categories.length) {
                return categories[choice - 1];
            }
            System.out.println("Invalid choice. Please choose between 1 and " + categories.length + ".");
        }
    }

    private PaymentMode promptPaymentMode() {
        PaymentMode[] modes = PaymentMode.values();
        while (true) {
            System.out.println("Select Payment Mode:");
            for (int i = 0; i < modes.length; i++) {
                System.out.printf("  %d. %s%n", (i + 1), modes[i]);
            }
            int choice = InputUtil.getInt("Enter payment mode choice (1-" + modes.length + "): ");
            if (choice >= 1 && choice <= modes.length) {
                return modes[choice - 1];
            }
            System.out.println("Invalid choice. Please choose between 1 and " + modes.length + ".");
        }
    }

    private LocalDate promptExpenseDate() {
        while (true) {
            String input = InputUtil.getString("Enter expense date (YYYY-MM-DD) or press Enter for today: ");
            if (input.isEmpty()) {
                return LocalDate.now();
            }
            if (ValidationUtil.isValidDate(input)) {
                return LocalDate.parse(input);
            }
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        }
    }
}