package com.littlepay.io;

import com.littlepay.model.Tap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TapReaderTest {

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
    void readParsesSixTaps(@TempDir Path dir) throws Exception {
        Path f = dir.resolve("taps.csv");
        Files.writeString(f, CSV);

        List<Tap> taps = new TapReader().read(f);

        assertEquals(6, taps.size());
        assertEquals("Stop1", taps.getFirst().stopId());
    }
}
