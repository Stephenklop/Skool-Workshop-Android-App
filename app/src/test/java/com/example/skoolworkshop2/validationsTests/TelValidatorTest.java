package com.example.skoolworkshop2.validationsTests;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.TelValidator;

public class TelValidatorTest {
    @Test
    public void telValidator_CorrectPhoneNumberGiven_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("0638422288"));
    }

    @Test
    public void telValidator_CorrectPhoneWith9Numbers_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("061242228"));
    }

    @Test
    public void telValidator_CorrectPhoneWith11Numbers_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("06222222221"));
    }

    @Test
    public void telValidator_CorrectPhoneWith15Numbers_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("062222222212312"));
    }

    @Test
    public void telValidator_InvalidPhoneWith16Numbers_ReturnsFalse() {
        assertFalse(TelValidator.isValidTelNumber("062222222212345"));
    }

    @Test
    public void telValidator_EmptyString_ReturnsFalse() {
        assertFalse(TelValidator.isValidTelNumber(""));
    }

    @Test
    public void telValidator_NullTel_ReturnsFalse() {
        assertFalse(TelValidator.isValidTelNumber(null));
    }

}
