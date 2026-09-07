package com.trackmint.ui;

import com.trackmint.model.DashboardSummary;
import com.trackmint.service.DashboardService;
import com.trackmint.util.FormatUtil;
import com.trackmint.util.InputUtil;

public class MainMenu {

    private final int userId;
    private final DashboardService dashboardService;
    private final ExpenseMenu expenseMenu;
    private final BudgetMenu budgetMenu;
    private final AnalyticsMenu analyticsMenu;

    public MainMenu(int userId) {
        this(userId, new DashboardService(), new ExpenseMenu(userId), new BudgetMenu(userId), new AnalyticsMenu(userId));
    }

    public MainMenu(int userId, DashboardService dashboardService, ExpenseMenu expenseMenu, BudgetMenu budgetMenu, AnalyticsMenu analyticsMenu) {
        this.userId = userId;
        this.dashboardService = dashboardService;
        this.expenseMenu = expenseMenu;
        this.budgetMenu = budgetMenu;
        this.analyticsMenu = analyticsMenu;
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n===== TrackMint Main Menu =====");
            System.out.println("1. View Dashboard");
            System.out.println("2. Expense Menu");
            System.out.println("3. Budget Menu");
            System.out.println("4. Analytics Menu");
            System.out.println("5. Logout");

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> showDashboard();
                case 2 -> expenseMenu.showMenu();
                case 3 -> budgetMenu.showBudgetMenu();
                case 4 -> analyticsMenu.showAnalyticsMenu();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showDashboard() {
        DashboardSummary summary = dashboardService.getDashboardSummary(userId);
        FormatUtil.printSection("DASHBOARD (" + summary.getMonth() + ")");
        System.out.println("Total Spent: " + FormatUtil.formatCurrency(summary.getTotalSpent()));
        if (summary.getRemainingBudget().isPresent()) {
            System.out.println("Remaining Budget: " + FormatUtil.formatCurrency(summary.getRemainingBudget().getAsDouble()));
        } else {
            System.out.println("Budget: No budget set for this month.");
        }
        System.out.println("Top Spending Category: " + summary.getTopCategory());
        FormatUtil.printLine();
    }
}