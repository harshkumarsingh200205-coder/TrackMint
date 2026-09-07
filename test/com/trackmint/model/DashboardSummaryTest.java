package com.trackmint.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

class DashboardSummaryTest {

    @Test
    @DisplayName("DashboardSummary holds immutable analytics metrics properly")
    void testDashboardSummaryFields() {
        DashboardSummary summary = new DashboardSummary("2026-09", 1500.00, OptionalDouble.of(8500.00), "FOOD");

        assertEquals("2026-09", summary.getMonth());
        assertEquals(1500.00, summary.getTotalSpent());
        assertTrue(summary.getRemainingBudget().isPresent());
        assertEquals(8500.00, summary.getRemainingBudget().getAsDouble());
        assertEquals("FOOD", summary.getTopCategory());
    }

    @Test
    @DisplayName("DashboardSummary handles empty budget gracefully")
    void testDashboardSummaryEmptyBudget() {
        DashboardSummary summary = new DashboardSummary("2026-09", 0.0, OptionalDouble.empty(), "No expenses found");

        assertEquals("2026-09", summary.getMonth());
        assertEquals(0.0, summary.getTotalSpent());
        assertFalse(summary.getRemainingBudget().isPresent());
        assertEquals("No expenses found", summary.getTopCategory());
    }
}
