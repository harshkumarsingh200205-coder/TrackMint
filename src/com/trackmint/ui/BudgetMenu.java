package com.trackmint.ui;

import com.trackmint.exception.DatabaseException;
import com.trackmint.model.Budget;
import com.trackmint.service.BudgetService;
import com.trackmint.util.FormatUtil;
import com.trackmint.util.InputUtil;
import com.trackmint.util.ValidationUtil;

public class BudgetMenu {
    private final BudgetService budgetService;
    private final int userId;

    public BudgetMenu(int userId) {
        this(userId, new BudgetService());
    }

    public BudgetMenu(int userId, BudgetService budgetService) {
        this.userId = userId;
        this.budgetService = budgetService;
    }

    public void showBudgetMenu() {
        while (true) {
            System.out.println("\n===== TrackMint Budget Menu =====");
            System.out.println("1. Set Monthly Budget");
            System.out.println("2. View Monthly Budget");
            System.out.println("3. Exit Budget Menu");

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> setBudget();
                case 2 -> viewBudget();
                case 3 -> {
                    System.out.println("Exiting Budget Menu.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void setBudget() {
        String month;
        do {
            month = InputUtil.getString("Enter month (YYYY-MM): ");
            if (!ValidationUtil.isValidMonth(month)) {
                System.out.println("Invalid month format. Use YYYY-MM.");
            }
        } while (!ValidationUtil.isValidMonth(month));

        double totalBudget;
        do {
            totalBudget = InputUtil.getDouble("Enter total budget: ");
            if (!ValidationUtil.isValidAmount(totalBudget)) {
                System.out.println("Budget must be greater than 0.");
            }
        } while (!ValidationUtil.isValidAmount(totalBudget));

        try {
            boolean saved = budgetService.setBudget(userId, month, totalBudget);
            if (saved) {
                System.out.println("Budget saved successfully.");
            } else {
                System.out.println("Failed to save budget.");
            }
        } catch (DatabaseException e) {
            System.out.println("Error saving budget: " + e.getMessage());
        }
    }

    private void viewBudget() {
        String month;
        do {
            month = InputUtil.getString("Enter month (YYYY-MM): ");
            if (!ValidationUtil.isValidMonth(month)) {
                System.out.println("Invalid month format. Use YYYY-MM.");
            }
        } while (!ValidationUtil.isValidMonth(month));

        try {
            Budget budget = budgetService.getBudgetByUserAndMonth(userId, month);

            if (budget == null) {
                System.out.println("No budget found for this month.");
            } else {
                FormatUtil.printSection("Budget Details");
                System.out.println("Month        : " + budget.getMonth());
                System.out.println("Total Budget : " + FormatUtil.formatCurrency(budget.getTotalBudget()));
                FormatUtil.printLine();
            }
        } catch (DatabaseException e) {
            System.out.println("Error fetching budget: " + e.getMessage());
        }
    }
}