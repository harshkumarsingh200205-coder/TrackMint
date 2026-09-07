package com.trackmint.service;

import com.trackmint.model.Budget;
import com.trackmint.repository.BudgetRepository;

public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService() {
        this(new BudgetRepository());
    }

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public boolean setBudget(int userId, String month, double totalBudget) {
        Budget budget = new Budget(0, userId, month, totalBudget);
        return budgetRepository.setBudget(budget);
    }

    public Budget getBudgetByUserAndMonth(int userId, String month) {
        return budgetRepository.getBudgetByUserAndMonth(userId, month);
    }
}