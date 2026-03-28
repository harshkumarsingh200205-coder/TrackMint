package com.trackmint.service;

public class DashboardService {

    private final AnalyticsService analyticsService = new AnalyticsService();

    public void showDashboard(int userId) {
        String currentMonth = java.time.YearMonth.now().toString();

        double totalSpent = analyticsService.getMonthlyTotal(userId, currentMonth);
        double remainingBudget = analyticsService.getRemainingBudget(userId, currentMonth);
        String topCategory = analyticsService.getTopCategory(userId, currentMonth);

        System.out.println("\n===== TrackMint Dashboard =====");
        System.out.println("Month: " + currentMonth);
        System.out.println("------------------------------");
        System.out.println("Total Spent: ₹" + totalSpent);
        System.out.println("Remaining Budget: ₹" + remainingBudget);
        System.out.println("Top Category: " + topCategory);
        System.out.println("------------------------------");
    }
}