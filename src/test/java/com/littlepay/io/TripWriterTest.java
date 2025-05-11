package com.littlepay.io;

import com.littlepay.model.Trip;
import com.littlepay.model.TripStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripWriterTest {

    @Test
    void writePersistsCsv(@TempDir Path dir) throws Exception {
        Trip t = new Trip(
                Instant.parse("2023-01-22T13:00:00Z"),
                Instant.parse("2023-01-22T13:05:00Z"),
                "Stop1", "Stop2",
                new BigDecimal("3.25"),
                "Company1", "Bus37", "5500005555555559", TripStatus.COMPLETED);

        Path out = dir.resolve("trips.csv");
        new TripWriter().write(out, List.of(t));

        List<String> lines = Files.readAllLines(out);
        assertEquals(2, lines.size());          // header + 1 row
        assertTrue(lines.get(1).contains("COMPLETED"));
    }
}
