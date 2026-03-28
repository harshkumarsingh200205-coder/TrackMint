package com.trackmint.app;

import com.trackmint.db.DatabaseInitializer;
import com.trackmint.model.User;
import com.trackmint.service.DashboardService;
import com.trackmint.ui.AuthMenu;
import com.trackmint.ui.ExpenseMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to TrackMint");
        DatabaseInitializer.initialize();

        AuthMenu authMenu = new AuthMenu();
        User loggedInUser = authMenu.showAuthMenu();

        if (loggedInUser != null) {

            // SHOW DASHBOARD FIRST
            DashboardService dashboardService = new DashboardService();
            dashboardService.showDashboard(loggedInUser.getId());

            // THEN OPEN MENU
            ExpenseMenu expenseMenu = new ExpenseMenu(loggedInUser.getId());
            expenseMenu.showMenu();
        }
    }
}