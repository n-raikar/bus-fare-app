package com.littlepay.service;

import com.littlepay.model.*;

import java.util.*;

/**
 * TripBuilder without Deque — tracks only the latest unmatched ON per key.
 */
public class TripBuilder {

    private final FareService fares;

    public TripBuilder(FareService fares) {
        this.fares = fares;
    }

    public List<Trip> build(List<Tap> taps) {
        // maps key -> single unmatched ON tap
        Map<String, Tap> open = new HashMap<>();
        List<Trip> trips = new ArrayList<>();

        taps.stream()
                .sorted(Comparator.comparing(Tap::dateTime))      // chronological
                .forEach(tap -> {
                    String k = key(tap);

                    if (tap.type() == TapType.ON) {
                        // if an ON already exists, close it as INCOMPLETE first
                        Tap prev = open.put(k, tap);
                        if (prev != null) trips.add(toIncomplete(prev));
                        return;
                    }

                    // OFF tap
                    Tap on = open.remove(k);
                    if (on != null) trips.add(toTrip(on, tap));
                    // orphan OFF => ignore
                });

        // remaining unmatched ONs → INCOMPLETE
        open.values().forEach(on -> trips.add(toIncomplete(on)));

        return trips;
    }

    /* ---------- helpers ---------- */

    private static String key(Tap t) {
        return t.pan() + '|' + t.busId() + '|' + t.companyId();
    }

    private Trip toTrip(Tap on, Tap off) {
        if (on.stopId().equals(off.stopId())) {
            return new Trip(on.dateTime(), off.dateTime(),
                    on.stopId(), off.stopId(),
                    java.math.BigDecimal.ZERO,
                    on.companyId(), on.busId(), on.pan(),
                    TripStatus.CANCELLED);
        }
        return new Trip(on.dateTime(), off.dateTime(),
                on.stopId(), off.stopId(),
                fares.fareBetween(on.stopId(), off.stopId()),
                on.companyId(), on.busId(), on.pan(),
                TripStatus.COMPLETED);
    }

    private Trip toIncomplete(Tap on) {
        return new Trip(on.dateTime(), null,
                on.stopId(), null,
                fares.maxFareFrom(on.stopId()),
                on.companyId(), on.busId(), on.pan(),
                TripStatus.INCOMPLETE);
    }
}
