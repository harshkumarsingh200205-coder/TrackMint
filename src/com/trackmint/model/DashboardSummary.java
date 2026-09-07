package com.trackmint.model;

import java.util.OptionalDouble;

public class DashboardSummary {
    private final String month;
    private final double totalSpent;
    private final OptionalDouble remainingBudget;
    private final String topCategory;

    public DashboardSummary(String month, double totalSpent, OptionalDouble remainingBudget, String topCategory) {
        this.month = month;
        this.totalSpent = totalSpent;
        this.remainingBudget = remainingBudget;
        this.topCategory = topCategory;
    }

    public String getMonth() {
        return month;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public OptionalDouble getRemainingBudget() {
        return remainingBudget;
    }

    public String getTopCategory() {
        return topCategory;
    }
}
