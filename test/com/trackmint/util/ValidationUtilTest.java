package com.trackmint.util;

import com.trackmint.model.Category;
import com.trackmint.model.PaymentMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    @DisplayName("Valid email formats should pass validation")
    void testValidEmail() {
        assertTrue(ValidationUtil.isValidEmail("user@example.com"));
        assertTrue(ValidationUtil.isValidEmail("first.last@domain.co.in"));
        assertTrue(ValidationUtil.isValidEmail("admin123@sub.domain.org"));
    }

    @Test
    @DisplayName("Invalid email formats should fail validation")
    void testInvalidEmail() {
        assertFalse(ValidationUtil.isValidEmail(""));
        assertFalse(ValidationUtil.isValidEmail(null));
        assertFalse(ValidationUtil.isValidEmail("plainaddress"));
        assertFalse(ValidationUtil.isValidEmail("@missingusername.com"));
        assertFalse(ValidationUtil.isValidEmail("username@.com"));
    }

    @Test
    @DisplayName("Valid amounts (> 0) should pass validation")
    void testValidAmount() {
        assertTrue(ValidationUtil.isValidAmount(0.01));
        assertTrue(ValidationUtil.isValidAmount(100.50));
        assertTrue(ValidationUtil.isValidAmount(1000000));
    }

    @Test
    @DisplayName("Zero or negative amounts should fail validation")
    void testInvalidAmount() {
        assertFalse(ValidationUtil.isValidAmount(0.0));
        assertFalse(ValidationUtil.isValidAmount(-50.0));
    }

    @Test
    @DisplayName("Valid dates in YYYY-MM-DD format should pass validation")
    void testValidDate() {
        assertTrue(ValidationUtil.isValidDate("2026-09-07"));
        assertTrue(ValidationUtil.isValidDate("2024-02-29")); // Leap year
    }

    @Test
    @DisplayName("Invalid date formats or values should fail validation")
    void testInvalidDate() {
        assertFalse(ValidationUtil.isValidDate("07-09-2026"));
        assertFalse(ValidationUtil.isValidDate("2026/09/07"));
        assertFalse(ValidationUtil.isValidDate("2026-02-30"));
        assertFalse(ValidationUtil.isValidDate(""));
        assertFalse(ValidationUtil.isValidDate(null));
    }

    @Test
    @DisplayName("Valid months in YYYY-MM format should pass validation")
    void testValidMonth() {
        assertTrue(ValidationUtil.isValidMonth("2026-09"));
        assertTrue(ValidationUtil.isValidMonth("2024-12"));
    }

    @Test
    @DisplayName("Invalid months should fail validation")
    void testInvalidMonth() {
        assertFalse(ValidationUtil.isValidMonth("2026-13"));
        assertFalse(ValidationUtil.isValidMonth("2026/09"));
        assertFalse(ValidationUtil.isValidMonth(""));
        assertFalse(ValidationUtil.isValidMonth(null));
    }

    @Test
    @DisplayName("Category parser should parse valid case-insensitive strings")
    void testParseCategory() {
        Optional<Category> cat = ValidationUtil.parseCategory("food");
        assertTrue(cat.isPresent());
        assertEquals(Category.FOOD, cat.get());

        Optional<Category> invalid = ValidationUtil.parseCategory("INVALID_CAT");
        assertFalse(invalid.isPresent());
    }

    @Test
    @DisplayName("PaymentMode parser should parse valid case-insensitive strings")
    void testParsePaymentMode() {
        Optional<PaymentMode> mode = ValidationUtil.parsePaymentMode("UPI");
        assertTrue(mode.isPresent());
        assertEquals(PaymentMode.UPI, mode.get());

        Optional<PaymentMode> invalid = ValidationUtil.parsePaymentMode("BITCOIN");
        assertFalse(invalid.isPresent());
    }
}
