package com.trackmint.service;

import com.trackmint.model.Category;
import com.trackmint.model.Expense;
import com.trackmint.model.PaymentMode;
import com.trackmint.repository.ExpenseRepository;
import java.time.LocalDate;
import java.util.List;

public class ExpenseService {
    private final ExpenseRepository expenseRepository = new ExpenseRepository();

    public void addExpense(int userId, String title, double amount, String category,
                           String paymentMode, String expenseDate, String notes) {
        Expense expense = new Expense(
                0,
                userId,
                title,
                amount,
                Category.valueOf(category.toUpperCase()),
                PaymentMode.valueOf(paymentMode.toUpperCase()),
                LocalDate.parse(expenseDate),
                notes
        );
        expenseRepository.addExpense(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.getAllExpenses();
    }

    public List<Expense> getAllExpensesByUser(int userId) {
    return expenseRepository.getAllExpensesByUser(userId);
    }

    public void updateExpense(int id, String title, double amount, String category,
                              String paymentMode, String expenseDate, String notes) {
        Expense expense = new Expense(
                id,
                1,
                title,
                amount,
                Category.valueOf(category.toUpperCase()),
                PaymentMode.valueOf(paymentMode.toUpperCase()),
                LocalDate.parse(expenseDate),
                notes
        );
        expenseRepository.updateExpense(expense);
    }

    public void deleteExpense(int id) {
        expenseRepository.deleteExpense(id);
    }
}