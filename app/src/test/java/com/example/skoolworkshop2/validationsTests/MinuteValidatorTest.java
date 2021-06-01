package com.example.skoolworkshop2.validationsTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.MinuteValidator;

import org.junit.Test;

public class MinuteValidatorTest {

    @Test
    public void minuteValidator_CorrectMinutesGiven_ReturnsTrue() {
        assertTrue(MinuteValidator.isValidMinute("50"));
    }

    @Test
    public void minuteValidator_CorrectMinimumMinutesGiven_ReturnsTrue() {
        assertTrue(MinuteValidator.isValidMinute("45"));
    }

    @Test
    public void minuteValidator_CorrectMaximumMinutesGiven_ReturnsTrue() {
        assertTrue(MinuteValidator.isValidMinute("120"));
    }

    @Test
    public void minuteValidator_InvalidMinutesMinimumGiven_ReturnsTrue() {
        assertFalse(MinuteValidator.isValidMinute("44"));
    }

    @Test
    public void minuteValidator_InvalidMinutesMaximumGiven_ReturnsFalse() {
        assertFalse(MinuteValidator.isValidMinute("121"));
    }

    @Test
    public void minuteValidator_InvalidMinutes150Given_ReturnsFalse() {
        assertFalse(MinuteValidator.isValidMinute("150"));
    }

    @Test
    public void minuteValidator_InvalidMinutes30Given_ReturnsFalse() {
        assertFalse(MinuteValidator.isValidMinute("30"));
    }

    @Test
    public void minuteValidator_InvalidMinutesMinus1Given_ReturnsFalse() {
        assertFalse(MinuteValidator.isValidMinute("-1"));
    }

    @Test
    public void minuteValidator_InvalidMinutes0Given_ReturnsFalse() {
        assertFalse(MinuteValidator.isValidMinute("0"));
    }
}
