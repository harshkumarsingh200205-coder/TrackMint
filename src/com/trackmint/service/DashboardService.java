package com.trackmint.service;

import com.trackmint.util.FormatUtil;
import java.util.OptionalDouble;

public class DashboardService {

    private final AnalyticsService analyticsService = new AnalyticsService();

    public void showDashboard(int userId) {
        String currentMonth = java.time.YearMonth.now().toString();

        double totalSpent = analyticsService.getMonthlyTotal(userId, currentMonth);
        OptionalDouble remainingBudget = analyticsService.getRemainingBudget(userId, currentMonth);
        String topCategory = analyticsService.getTopCategory(userId, currentMonth);

        FormatUtil.printSection("TrackMint Dashboard");
        System.out.println("Month            : " + currentMonth);
        System.out.println("Total Spent      : " + FormatUtil.formatCurrency(totalSpent));

        if (remainingBudget.isEmpty()) {
            System.out.println("Remaining Budget : Not set for this month");
        } else {
            double rem = remainingBudget.getAsDouble();
            if (rem < 0) {
                System.out.println("Remaining Budget : " + FormatUtil.formatCurrency(rem) + " (Exceeded by " + FormatUtil.formatCurrency(Math.abs(rem)) + "!)");
            } else {
                System.out.println("Remaining Budget : " + FormatUtil.formatCurrency(rem));
            }
        }

        System.out.println("Top Category     : " + topCategory);
        FormatUtil.printLine();
    }
}