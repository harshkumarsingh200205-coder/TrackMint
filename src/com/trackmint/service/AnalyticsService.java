package com.trackmint.service;

import com.trackmint.model.Budget;

import java.util.Map;
import java.util.OptionalDouble;

public class AnalyticsService {
    private final ExpenseService expenseService = new ExpenseService();
    private final BudgetService budgetService = new BudgetService();

    public double getMonthlyTotal(int userId, String month) {
        return expenseService.getMonthlyTotal(userId, month);
    }

    public Map<String, Double> getCategoryWiseTotal(int userId, String month) {
        return expenseService.getCategoryWiseTotal(userId, month);
    }

    public OptionalDouble getRemainingBudget(int userId, String month) {
        Budget budget = budgetService.getBudgetByUserAndMonth(userId, month);

        if (budget == null) {
            return OptionalDouble.empty();
        }

        return OptionalDouble.of(budget.getTotalBudget() - getMonthlyTotal(userId, month));
    }

    public String getTopCategory(int userId, String month) {
        return expenseService.getTopCategory(userId, month);
    }
}