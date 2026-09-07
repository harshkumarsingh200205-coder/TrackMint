package com.trackmint.service;

import com.trackmint.model.Budget;

import java.util.Map;
import java.util.OptionalDouble;

public class AnalyticsService {
    private final ExpenseService expenseService;
    private final BudgetService budgetService;

    public AnalyticsService() {
        this(new ExpenseService(), new BudgetService());
    }

    public AnalyticsService(ExpenseService expenseService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.budgetService = budgetService;
    }

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