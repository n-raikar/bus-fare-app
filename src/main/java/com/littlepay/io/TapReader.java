package com.littlepay.io;

import com.littlepay.model.Tap;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TapReader {

    public List<Tap> read(Path csv) throws Exception {
        var taps = new ArrayList<Tap>();

        try (CSVReader rdr = new CSVReaderBuilder(Files.newBufferedReader(csv))
                .withSkipLines(1)   // skip header
                .build()) {

            String[] row;
            while ((row = rdr.readNext()) != null) {
                taps.add(Tap.fromRow(row));
            }
        }
        return taps;
    }
}

