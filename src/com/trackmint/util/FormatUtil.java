package com.trackmint.util;

public class FormatUtil {

    public static String formatCurrency(double amount) {
        return String.format("₹%.2f", amount);
    }

    public static void printLine() {
        System.out.println("--------------------------------------------------");
    }

    public static void printSection(String title) {
        System.out.println();
        printLine();
        System.out.println(title);
        printLine();
    }
}