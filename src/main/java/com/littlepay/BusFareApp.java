package com.littlepay;

import com.littlepay.io.TapReader;
import com.littlepay.io.TripWriter;
import com.littlepay.service.FareService;
import com.littlepay.service.TripBuilder;
import com.littlepay.model.Tap;
import com.littlepay.model.Trip;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BusFareApp {
    private static final Logger log = Logger.getLogger(BusFareApp.class.getName());

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: BusFareApp <taps.csv> <trips.csv>");
            System.exit(1);
        }

        Path in  = Paths.get(args[0]);
        Path out = Paths.get(args[1]);

        try {
            List<Tap>  taps  = new TapReader().read(in);
            List<Trip> trips = new TripBuilder(new FareService()).build(taps);
            new TripWriter().write(out, trips);
            log.info("Done. Trips written: " + trips.size());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Processing failed", e);
            System.exit(2);
        }
    }
}
