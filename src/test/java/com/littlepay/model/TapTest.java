package com.littlepay.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TapTest {

    @Test
    void fromRowParseCsvRow() {
        String[] row = {
                "1",  "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559"
        };
        Tap tap = Tap.fromRow(row);

        assertEquals(1, tap.id());
        assertEquals(Instant.parse("2023-01-22T13:00:00Z"), tap.dateTime());
        assertEquals(TapType.ON, tap.type());
        assertEquals("Stop1", tap.stopId());

    }
}
