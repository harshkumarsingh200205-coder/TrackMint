package com.trackmint.service;

import com.trackmint.model.Category;
import com.trackmint.model.Expense;
import com.trackmint.model.PaymentMode;
import com.trackmint.repository.ExpenseRepository;
import com.trackmint.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService() {
        this(new ExpenseRepository());
    }

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public boolean addExpense(int userId, String title, double amount, Category category,
                              PaymentMode paymentMode, LocalDate expenseDate, String notes) {
        Expense expense = new Expense(
                0,
                userId,
                title,
                amount,
                category,
                paymentMode,
                expenseDate,
                notes
        );
        return expenseRepository.addExpense(expense);
    }

    public boolean addExpense(int userId, String title, double amount, String category,
                              String paymentMode, String expenseDate, String notes) {
        Category cat = ValidationUtil.parseCategory(category).orElse(Category.OTHERS);
        PaymentMode mode = ValidationUtil.parsePaymentMode(paymentMode).orElse(PaymentMode.CASH);
        LocalDate date = ValidationUtil.isValidDate(expenseDate) ? LocalDate.parse(expenseDate) : LocalDate.now();
        return addExpense(userId, title, amount, cat, mode, date, notes);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.getAllExpenses();
    }

    public List<Expense> getAllExpensesByUser(int userId) {
        return expenseRepository.getAllExpensesByUser(userId);
    }

    public Expense getExpenseByIdAndUser(int id, int userId) {
        return expenseRepository.getExpenseByIdAndUser(id, userId);
    }

    public boolean updateExpense(int id, int userId, String title, double amount, Category category,
                                 PaymentMode paymentMode, LocalDate expenseDate, String notes) {
        Expense expense = new Expense(
                id,
                userId,
                title,
                amount,
                category,
                paymentMode,
                expenseDate,
                notes
        );
        return expenseRepository.updateExpense(expense);
    }

    public boolean updateExpense(int id, int userId, String title, double amount, String category,
                                 String paymentMode, String expenseDate, String notes) {
        Category cat = ValidationUtil.parseCategory(category).orElse(Category.OTHERS);
        PaymentMode mode = ValidationUtil.parsePaymentMode(paymentMode).orElse(PaymentMode.CASH);
        LocalDate date = ValidationUtil.isValidDate(expenseDate) ? LocalDate.parse(expenseDate) : LocalDate.now();
        return updateExpense(id, userId, title, amount, cat, mode, date, notes);
    }

    public boolean deleteExpense(int id, int userId) {
        return expenseRepository.deleteExpense(id, userId);
    }

    public double getMonthlyTotal(int userId, String month) {
        return expenseRepository.getMonthlyTotal(userId, month);
    }

    public Map<String, Double> getCategoryWiseTotal(int userId, String month) {
        return expenseRepository.getCategoryWiseTotal(userId, month);
    }

    public String getTopCategory(int userId, String month) {
        return expenseRepository.getTopCategory(userId, month);
    }
}