package com.trackmint.util;

import com.trackmint.exception.TrackMintException;
import com.trackmint.model.Category;
import com.trackmint.model.Expense;
import com.trackmint.model.PaymentMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

    @Test
    @DisplayName("Exporting empty list should throw TrackMintException")
    void testExportEmptyList() {
        assertThrows(TrackMintException.class, () -> CsvExporter.exportExpenses(1, Collections.emptyList()));
        assertThrows(TrackMintException.class, () -> CsvExporter.exportExpenses(1, null));
    }

    @Test
    @DisplayName("Exporting valid expenses generates accessible CSV file with headers")
    void testExportValidExpenses() throws IOException {
        Expense expense = new Expense(
                1,
                99,
                "Book, Special Edition",
                450.00,
                Category.EDUCATION,
                PaymentMode.DEBIT_CARD,
                LocalDate.of(2026, 9, 7),
                "Gift \"Notes\" included"
        );

        String path = CsvExporter.exportExpenses(99, List.of(expense));
        assertNotNull(path);

        File file = new File(path);
        assertTrue(file.exists(), "CSV file should be created");

        List<String> lines = Files.readAllLines(file.toPath());
        assertTrue(lines.size() >= 2, "File should have at least header + 1 row");
        assertEquals("ID,Date,Title,Category,PaymentMode,Amount,Notes", lines.get(0));

        // Clean up
        file.delete();
    }
}
