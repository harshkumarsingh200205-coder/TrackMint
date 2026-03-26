package com.trackmint.app;

import com.trackmint.db.DatabaseInitializer;
import com.trackmint.ui.ExpenseMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to TrackMint");
        DatabaseInitializer.initialize();

        ExpenseMenu expenseMenu = new ExpenseMenu();
        expenseMenu.showMenu();
    }
}