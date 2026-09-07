package com.trackmint.util;

import com.trackmint.exception.TrackMintException;
import com.trackmint.model.Expense;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvExporter {

    private static final String EXPORT_DIR = "exports";

    public static String exportExpenses(int userId, List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) {
            throw new TrackMintException("No expenses available to export.");
        }

        File directory = new File(EXPORT_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created && !directory.exists()) {
                throw new TrackMintException("Failed to create exports directory: " + EXPORT_DIR);
            }
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = String.format("%s/expenses_user%d_%s.csv", EXPORT_DIR, userId, timestamp);
        File file = new File(filename);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // CSV Header
            writer.println("ID,Date,Title,Category,PaymentMode,Amount,Notes");

            // Rows
            for (Expense exp : expenses) {
                writer.printf("%d,%s,%s,%s,%s,%.2f,%s%n",
                        exp.getId(),
                        escapeCsv(exp.getExpenseDate().toString()),
                        escapeCsv(exp.getTitle()),
                        escapeCsv(exp.getCategory().name()),
                        escapeCsv(exp.getPaymentMode().name()),
                        exp.getAmount(),
                        escapeCsv(exp.getNotes() != null ? exp.getNotes() : ""));
            }
        } catch (IOException e) {
            throw new TrackMintException("Failed to write CSV file: " + e.getMessage(), e);
        }

        return file.getAbsolutePath();
    }

    private static String escapeCsv(String data) {
        if (data == null) {
            return "\"\"";
        }
        String escaped = data.replace("\"", "\"\"");
        if (data.contains(",") || data.contains("\"") || data.contains("\n") || data.contains("\r")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}
