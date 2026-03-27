package com.trackmint.service;

import com.trackmint.model.Budget;
import com.trackmint.repository.BudgetRepository;

public class BudgetService {
    private final BudgetRepository budgetRepository = new BudgetRepository();

    public void setBudget(int userId, String month, double totalBudget) {
        Budget budget = new Budget(0, userId, month, totalBudget);
        budgetRepository.setBudget(budget);
    }

    public Budget getBudgetByUserAndMonth(int userId, String month) {
        return budgetRepository.getBudgetByUserAndMonth(userId, month);
    }
}