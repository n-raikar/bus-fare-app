package com.littlepay.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public record Trip(
        Instant started,
        Instant finished,
        String fromStopId,
        String toStopId,
        BigDecimal chargeAmount,
        String companyId,
        String busId,
        String pan,
        TripStatus status) {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneOffset.UTC);

    public long durationSeconds() {
        return (started != null && finished != null)
                ? Duration.between(started, finished).getSeconds()
                : 0;
    }

    /** Convert to a CSV row. */
    public String[] toRow() {
        return new String[]{
                FMT.format(started),
                finished == null ? "" : FMT.format(finished),
                String.valueOf(durationSeconds()),
                fromStopId,
                toStopId == null ? "" : toStopId,
                "$" + chargeAmount,
                companyId,
                busId,
                pan,
                status.name()
        };
    }
}
