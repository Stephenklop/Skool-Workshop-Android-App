package com.example.skoolworkshop2.validationsTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.RoundsValidator;

import org.junit.Test;

public class RoundValidatorTest {

    @Test
    public void roundValidator_CorrectRoundsGiven_ReturnsTrue() {
        assertTrue(RoundsValidator.isValidWorkshopRounds("3"));
    }

    @Test
    public void roundValidator_CorrectRounds10Given_ReturnsTrue() {
        assertTrue(RoundsValidator.isValidWorkshopRounds("10"));
    }

    @Test
    public void roundValidator_InvalidRoundsMinus1Given_ReturnsTrue() {
        assertFalse(RoundsValidator.isValidWorkshopRounds("-1"));
    }

    @Test
    public void roundValidator_InvalidRounds0Given_ReturnsTrue() {
        assertFalse(RoundsValidator.isValidWorkshopRounds("0"));
    }
}
