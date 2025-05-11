package com.littlepay.io;

import com.littlepay.model.Trip;
import com.opencsv.CSVWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TripWriter {

    private static final String[] HEADER = {
            "Started", "Finished", "DurationSecs", "FromStopId", "ToStopId",
            "ChargeAmount", "CompanyId", "BusID", "PAN", "Status"
    };

    public void write(Path csv, List<Trip> trips) throws Exception {
        try (CSVWriter w = new CSVWriter(Files.newBufferedWriter(csv))) {
            w.writeNext(HEADER);
            for (Trip t : trips) w.writeNext(t.toRow());
        }
    }
}

