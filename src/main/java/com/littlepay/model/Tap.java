package com.littlepay.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public record Tap(
        long id,
        Instant dateTime,
        TapType type,
        String stopId,
        String companyId,
        String busId,
        String pan) {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /** Parse a CSV row (already split). */
    public static Tap fromRow(String[] r) {
        return new Tap(
                Long.parseLong(r[0].trim()),
                LocalDateTime.parse(r[1].trim(), FMT).toInstant(ZoneOffset.UTC),
                TapType.valueOf(r[2].trim()),
                r[3].trim(),
                r[4].trim(),
                r[5].trim(),
                r[6].trim());
    }
}
