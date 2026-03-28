package com.trackmint.app;

import com.trackmint.db.DatabaseInitializer;
import com.trackmint.model.User;
import com.trackmint.ui.AuthMenu;
import com.trackmint.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to TrackMint");
        DatabaseInitializer.initialize();

        while (true) {
            AuthMenu authMenu = new AuthMenu();
            User loggedInUser = authMenu.showAuthMenu();

            if (loggedInUser == null) {
                System.out.println("Exiting TrackMint.");
                break;
            }

            MainMenu mainMenu = new MainMenu(loggedInUser.getId());
            mainMenu.showMainMenu();
        }
    }
}