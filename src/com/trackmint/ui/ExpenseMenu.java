package com.trackmint.ui;

import com.trackmint.model.Expense;
import com.trackmint.service.ExpenseService;
import com.trackmint.util.InputUtil;

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
            System.out.println("5. Exit");

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewAllExpenses();
                case 3 -> updateExpense();
                case 4 -> deleteExpense();
                case 5 -> {
                    System.out.println("Exiting Expense Menu.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addExpense() {
        String title = InputUtil.getString("Enter title: ");
        double amount = InputUtil.getDouble("Enter amount: ");
        String category = InputUtil.getString("Enter category (FOOD, TRAVEL, SHOPPING, BILLS, HEALTH, EDUCATION, ENTERTAINMENT, OTHERS): ");
        String paymentMode = InputUtil.getString("Enter payment mode (CASH, UPI, DEBIT_CARD, CREDIT_CARD, NET_BANKING): ");
        String expenseDate = InputUtil.getString("Enter expense date (YYYY-MM-DD): ");
        String notes = InputUtil.getString("Enter notes: ");

        expenseService.addExpense(userId, title, amount, category, paymentMode, expenseDate, notes);
    }

    private void viewAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpensesByUser(userId);

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("\n===== All Expenses =====");
        for (Expense expense : expenses) {
            System.out.println("ID: " + expense.getId());
            System.out.println("Title: " + expense.getTitle());
            System.out.println("Amount: " + expense.getAmount());
            System.out.println("Category: " + expense.getCategory());
            System.out.println("Payment Mode: " + expense.getPaymentMode());
            System.out.println("Date: " + expense.getExpenseDate());
            System.out.println("Notes: " + expense.getNotes());
            System.out.println("------------------------");
        }
    }

    private void updateExpense() {
        int id = InputUtil.getInt("Enter expense ID to update: ");
        String title = InputUtil.getString("Enter new title: ");
        double amount = InputUtil.getDouble("Enter new amount: ");
        String category = InputUtil.getString("Enter new category: ");
        String paymentMode = InputUtil.getString("Enter new payment mode: ");
        String expenseDate = InputUtil.getString("Enter new expense date (YYYY-MM-DD): ");
        String notes = InputUtil.getString("Enter new notes: ");

        expenseService.updateExpense(id, title, amount, category, paymentMode, expenseDate, notes);
    }

    private void deleteExpense() {
        int id = InputUtil.getInt("Enter expense ID to delete: ");
        expenseService.deleteExpense(id);
    }
}