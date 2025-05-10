package com.littlepay.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FareServiceTest {
    private final FareService fareService = new FareService();

    @Test
    void maxFareFromReturnsHighestFare() {
        assertEquals(new BigDecimal("7.30"), fareService.maxFareFrom("Stop3"));
    }

    @Test
    void fareBetWeenIsSymmetric() {
        assertEquals(new BigDecimal("3.25"), fareService.fareBetween("Stop1", "Stop2"));
        assertEquals(new BigDecimal("3.25"), fareService.fareBetween("Stop2", "Stop1"));
    }

    @Test
    void unknownPairReturnsNull() {
        assertNull(fareService.fareBetween("StopA", "StopB"));
    }
}
