package com.trackmint.util;

import com.trackmint.model.Expense;

import java.util.List;

public class TableUtil {

    private static final int ID_WIDTH = 6;
    private static final int DATE_WIDTH = 12;
    private static final int TITLE_WIDTH = 22;
    private static final int CATEGORY_WIDTH = 14;
    private static final int PAYMENT_WIDTH = 14;
    private static final int AMOUNT_WIDTH = 13;
    private static final int NOTES_WIDTH = 24;

    public static void printExpenseTable(List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        String separator = buildSeparator();

        System.out.println(separator);
        System.out.printf("| %-" + ID_WIDTH + "s | %-" + DATE_WIDTH + "s | %-" + TITLE_WIDTH + "s | %-" + CATEGORY_WIDTH + "s | %-" + PAYMENT_WIDTH + "s | %" + AMOUNT_WIDTH + "s | %-" + NOTES_WIDTH + "s |%n",
                "ID", "Date", "Title", "Category", "Payment Mode", "Amount", "Notes");
        System.out.println(separator);

        double totalAmount = 0;
        for (Expense exp : expenses) {
            totalAmount += exp.getAmount();
            String title = truncate(exp.getTitle(), TITLE_WIDTH);
            String notes = truncate(exp.getNotes() != null ? exp.getNotes() : "-", NOTES_WIDTH);
            String amountFormatted = FormatUtil.formatCurrency(exp.getAmount());

            System.out.printf("| %-" + ID_WIDTH + "d | %-" + DATE_WIDTH + "s | %-" + TITLE_WIDTH + "s | %-" + CATEGORY_WIDTH + "s | %-" + PAYMENT_WIDTH + "s | %" + AMOUNT_WIDTH + "s | %-" + NOTES_WIDTH + "s |%n",
                    exp.getId(),
                    exp.getExpenseDate(),
                    title,
                    exp.getCategory(),
                    exp.getPaymentMode(),
                    amountFormatted,
                    notes);
        }

        System.out.println(separator);
        System.out.printf("| %-" + (ID_WIDTH + DATE_WIDTH + TITLE_WIDTH + CATEGORY_WIDTH + PAYMENT_WIDTH + 10) + "s | %" + AMOUNT_WIDTH + "s | %-" + NOTES_WIDTH + "s |%n",
                "TOTAL (" + expenses.size() + " records)",
                FormatUtil.formatCurrency(totalAmount),
                "");
        System.out.println(separator);
    }

    private static String buildSeparator() {
        return "+-" + "-".repeat(ID_WIDTH) +
                "-+-" + "-".repeat(DATE_WIDTH) +
                "-+-" + "-".repeat(TITLE_WIDTH) +
                "-+-" + "-".repeat(CATEGORY_WIDTH) +
                "-+-" + "-".repeat(PAYMENT_WIDTH) +
                "-+-" + "-".repeat(AMOUNT_WIDTH) +
                "-+-" + "-".repeat(NOTES_WIDTH) + "-+";
    }

    private static String truncate(String text, int maxWidth) {
        if (text == null) return "";
        if (text.length() <= maxWidth) return text;
        return text.substring(0, maxWidth - 3) + "...";
    }
}
