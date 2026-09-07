package com.trackmint.service;

import com.trackmint.model.DashboardSummary;

import java.time.YearMonth;
import java.util.OptionalDouble;

public class DashboardService {
    private final AnalyticsService analyticsService;

    public DashboardService() {
        this(new AnalyticsService());
    }

    public DashboardService(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    public DashboardSummary getDashboardSummary(int userId) {
        String currentMonth = YearMonth.now().toString();

        double totalSpent = analyticsService.getMonthlyTotal(userId, currentMonth);
        OptionalDouble remainingBudget = analyticsService.getRemainingBudget(userId, currentMonth);
        String topCategory = analyticsService.getTopCategory(userId, currentMonth);

        return new DashboardSummary(currentMonth, totalSpent, remainingBudget, topCategory);
    }
}