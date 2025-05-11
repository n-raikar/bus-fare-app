package com.littlepay.service;

import com.littlepay.io.TapReader;
import com.littlepay.model.Trip;
import com.littlepay.model.TripStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TripBuilderTest {

    private static final String CSV =
            """
            ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
            1, 22-01-2023 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
            2, 22-01-2023 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559
            3, 22-01-2023 09:20:00, ON, Stop3, Company1, Bus36, 4111111111111111
            4, 23-01-2023 08:00:00, ON, Stop1, Company1, Bus37, 4111111111111111
            5, 23-01-2023 08:02:00, OFF, Stop1, Company1, Bus37, 4111111111111111
            6, 24-01-2023 16:30:00, OFF, Stop2, Company1, Bus37, 5500005555555559
            """;

    @Test
    void buildCreatesThreeTripsWithCorrectStatuses(@TempDir Path dir) throws Exception {
        Path tapsFile = dir.resolve("taps.csv");
        Files.writeString(tapsFile, CSV);

        var taps = new TapReader().read(tapsFile);
        List<Trip> trips = new TripBuilder(new FareService()).build(taps);

        assertEquals(3, trips.size());

        Map<TripStatus, Long> counts = trips.stream()
                .collect(Collectors.groupingBy(Trip::status, Collectors.counting()));

        assertEquals(1, counts.get(TripStatus.COMPLETED));
        assertEquals(1, counts.get(TripStatus.CANCELLED));
        assertEquals(1, counts.get(TripStatus.INCOMPLETE));

        Trip completed = trips.stream()
                .filter(t -> t.status() == TripStatus.COMPLETED)
                .findFirst().orElseThrow();

        assertEquals(new BigDecimal("3.25"), completed.chargeAmount());
        assertEquals(300, completed.durationSeconds());
    }
}
