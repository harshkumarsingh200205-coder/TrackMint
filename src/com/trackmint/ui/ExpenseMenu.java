package com.trackmint.ui;

import com.trackmint.model.Category;
import com.trackmint.model.Expense;
import com.trackmint.model.PaymentMode;
import com.trackmint.service.ExpenseService;
import com.trackmint.util.FormatUtil;
import com.trackmint.util.InputUtil;
import com.trackmint.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

public class ExpenseMenu {

    private final ExpenseService expenseService = new ExpenseService();
    private final int userId;

    public ExpenseMenu(int userId) {
        this.userId = userId;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n===== TrackMint Expense Menu =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Back");

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewAllExpenses();
                case 3 -> updateExpense();
                case 4 -> deleteExpense();
                case 5 -> {
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

        expenseService.addExpense(userId, title, amount, category, paymentMode, expenseDate, notes);
    }

    private void viewAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpensesByUser(userId);

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        FormatUtil.printSection("All Expenses");

        for (Expense expense : expenses) {
            System.out.println("ID           : " + expense.getId());
            System.out.println("Title        : " + expense.getTitle());
            System.out.println("Amount       : " + FormatUtil.formatCurrency(expense.getAmount()));
            System.out.println("Category     : " + expense.getCategory());
            System.out.println("Payment Mode : " + expense.getPaymentMode());
            System.out.println("Date         : " + expense.getExpenseDate());
            System.out.println("Notes        : " + expense.getNotes());
            FormatUtil.printLine();
        }
    }

    private void updateExpense() {
        int id = InputUtil.getInt("Enter expense ID to update: ");
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

        expenseService.updateExpense(id, userId, title, amount, category, paymentMode, expenseDate, notes);
    }

    private void deleteExpense() {
        int id = InputUtil.getInt("Enter expense ID to delete: ");
        Expense existing = expenseService.getExpenseByIdAndUser(id, userId);
        if (existing == null) {
            System.out.println("Expense with ID " + id + " not found or does not belong to you.");
            return;
        }

        String confirm = InputUtil.getString("Are you sure you want to delete '" + existing.getTitle() + "' (ID: " + id + ")? (y/N): ");
        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            expenseService.deleteExpense(id, userId);
        } else {
            System.out.println("Deletion cancelled.");
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