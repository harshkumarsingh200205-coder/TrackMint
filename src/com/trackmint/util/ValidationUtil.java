package com.trackmint.util;

import com.trackmint.model.Category;
import com.trackmint.model.PaymentMode;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Optional;

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

    public static Optional<Category> parseCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Category.valueOf(category.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static boolean isValidCategory(String category) {
        return parseCategory(category).isPresent();
    }

    public static Optional<PaymentMode> parsePaymentMode(String paymentMode) {
        if (paymentMode == null || paymentMode.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(PaymentMode.valueOf(paymentMode.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static boolean isValidPaymentMode(String paymentMode) {
        return parsePaymentMode(paymentMode).isPresent();
    }
}