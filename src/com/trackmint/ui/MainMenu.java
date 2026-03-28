package com.trackmint.ui;

import com.trackmint.service.DashboardService;
import com.trackmint.util.InputUtil;

public class MainMenu {

    private final int userId;

    public MainMenu(int userId) {
        this.userId = userId;
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
                case 1 -> {
                    DashboardService dashboardService = new DashboardService();
                    dashboardService.showDashboard(userId);
                }
                case 2 -> {
                    ExpenseMenu expenseMenu = new ExpenseMenu(userId);
                    expenseMenu.showMenu();
                }
                case 3 -> {
                    BudgetMenu budgetMenu = new BudgetMenu(userId);
                    budgetMenu.showBudgetMenu();
                }
                case 4 -> {
                    AnalyticsMenu analyticsMenu = new AnalyticsMenu(userId);
                    analyticsMenu.showAnalyticsMenu();
                }
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}