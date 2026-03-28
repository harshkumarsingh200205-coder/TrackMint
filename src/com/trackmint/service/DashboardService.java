package com.trackmint.service;

import com.trackmint.util.FormatUtil;

public class DashboardService {

    private final AnalyticsService analyticsService = new AnalyticsService();

    public void showDashboard(int userId) {
        String currentMonth = java.time.YearMonth.now().toString();

        double totalSpent = analyticsService.getMonthlyTotal(userId, currentMonth);
        double remainingBudget = analyticsService.getRemainingBudget(userId, currentMonth);
        String topCategory = analyticsService.getTopCategory(userId, currentMonth);

        FormatUtil.printSection("TrackMint Dashboard");
        System.out.println("Month            : " + currentMonth);
        System.out.println("Total Spent      : " + FormatUtil.formatCurrency(totalSpent));
        System.out.println("Remaining Budget : " + FormatUtil.formatCurrency(remainingBudget));
        System.out.println("Top Category     : " + topCategory);
        FormatUtil.printLine();
    }
}