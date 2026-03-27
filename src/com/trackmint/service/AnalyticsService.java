package com.trackmint.service;

import com.trackmint.model.Budget;
import com.trackmint.model.Expense;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsService {
    private final ExpenseService expenseService = new ExpenseService();
    private final BudgetService budgetService = new BudgetService();

    public double getMonthlyTotal(int userId, String month) {
        List<Expense> expenses = expenseService.getAllExpensesByUser(userId);

        return expenses.stream()
                .filter(expense -> YearMonth.from(expense.getExpenseDate()).toString().equals(month))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<String, Double> getCategoryWiseTotal(int userId, String month) {
        List<Expense> expenses = expenseService.getAllExpensesByUser(userId);
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            if (YearMonth.from(expense.getExpenseDate()).toString().equals(month)) {
                String category = expense.getCategory().name();
                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + expense.getAmount());
            }
        }

        return categoryTotals;
    }

    public double getRemainingBudget(int userId, String month) {
        Budget budget = budgetService.getBudgetByUserAndMonth(userId, month);

        if (budget == null) {
            return 0;
        }

        return budget.getTotalBudget() - getMonthlyTotal(userId, month);
    }

    public String getTopCategory(int userId, String month) {
        Map<String, Double> categoryTotals = getCategoryWiseTotal(userId, month);

        String topCategory = "None";
        double max = 0;

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        return topCategory;
    }
}