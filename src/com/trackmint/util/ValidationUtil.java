package com.trackmint.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidMonth(String month) {
        try {
            YearMonth.parse(month);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}