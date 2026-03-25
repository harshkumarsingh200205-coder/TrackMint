package com.trackmint.app;

import com.trackmint.db.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to TrackMint!");
        DatabaseInitializer.initialize();
    }
    
}