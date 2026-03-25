package com.trackmint.model;

public class Budget {
    private int id;
    private int userId;
    private String month;
    private double totalBudget;

    public Budget() {
    }

    public Budget(int id, int userId, String month, double totalBudget) {
        this.id = id;
        this.userId = userId;
        this.month = month;
        this.totalBudget = totalBudget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }
}
