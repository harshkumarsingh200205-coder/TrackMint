package com.trackmint.ui;

import com.trackmint.service.AnalyticsService;
import com.trackmint.util.InputUtil;
import com.trackmint.util.ValidationUtil;
import java.util.Map;

public class AnalyticsMenu {
    private final AnalyticsService analyticsService = new AnalyticsService();
    private final int userId;

    public AnalyticsMenu(int userId) {
        this.userId = userId;
    }

    public void showAnalyticsMenu() {
        while (true) {
            System.out.println("\n===== TrackMint Analytics Menu =====");
            System.out.println("1. View Monthly Total");
            System.out.println("2. View Category-wise Summary");
            System.out.println("3. View Remaining Budget");
            System.out.println("4. View Top Spending Category");
            System.out.println("5. Exit Analytics Menu");

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> viewMonthlyTotal();
                case 2 -> viewCategoryWiseSummary();
                case 3 -> viewRemainingBudget();
                case 4 -> viewTopCategory();
                case 5 -> {
                    System.out.println("Exiting Analytics Menu.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private String getValidMonth() {
    String month;
    do {
        month = InputUtil.getString("Enter month (YYYY-MM): ");
        if (!ValidationUtil.isValidMonth(month)) {
            System.out.println("Invalid month format. Use YYYY-MM.");
        }
    } while (!ValidationUtil.isValidMonth(month));

    return month;
}

    private void viewMonthlyTotal() {
    String month = getValidMonth();
    double total = analyticsService.getMonthlyTotal(userId, month);

    System.out.println("\n===== Monthly Total =====");
    System.out.println("Month: " + month);
    System.out.println("Total Spent: " + total);
}

    private void viewCategoryWiseSummary() {
    String month = getValidMonth();
    Map<String, Double> categoryTotals = analyticsService.getCategoryWiseTotal(userId, month);

    System.out.println("\n===== Category-wise Summary =====");
    if (categoryTotals.isEmpty()) {
        System.out.println("No expenses found for this month.");
        return;
    }

    for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }
}

    private void viewRemainingBudget() {
        String month = getValidMonth();
        double remaining = analyticsService.getRemainingBudget(userId, month);

        System.out.println("\n===== Remaining Budget =====");
        System.out.println("Month: " + month);
        System.out.println("Remaining Budget: " + remaining);
    }

    private void viewTopCategory() {
        String month = getValidMonth();
        String topCategory = analyticsService.getTopCategory(userId, month);

        System.out.println("\n===== Top Spending Category =====");
        System.out.println("Month: " + month);
        System.out.println("Top Category: " + topCategory);
    }
}