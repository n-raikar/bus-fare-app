package com.littlepay.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class FareService {

    private static final Map<Set<String>, BigDecimal> FARES = Map.of(
            Set.of("Stop1", "Stop2"), new BigDecimal("3.25"),
            Set.of("Stop2", "Stop3"), new BigDecimal("5.50"),
            Set.of("Stop1", "Stop3"), new BigDecimal("7.30")
    );

    public BigDecimal fareBetween(String start, String end) {
        return FARES.get(Set.of(start, end));
    }

    public BigDecimal maxFareFrom(String stop) {
        return FARES.entrySet().stream()
                .filter(entry -> entry.getKey().contains(stop))
                .map(Map.Entry::getValue)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }
}
