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
    public void telValidator_CorrectPhoneInternationalAnnotationNL_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("+31101234567"));
    }
    @Test
    public void telValidator_CorrectPhoneInternationalAnnotationNL2_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("+31(0) 10123 4567"));
    }
    @Test
    public void telValidator_CorrectPhoneInternationalAnnotationNL3_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("+3110-1234567"));
    }
    @Test
    public void telValidator_CorrectPhoneInternationalAnnotationBE_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("+32101234567"));
    }
    @Test
    public void telValidator_CorrectPhoneInternationalAnnotationBE2_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("+32(0) 10123 4567"));
    }
    @Test
    public void telValidator_CorrectPhoneInternationalAnnotationBE3_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("+3210-1234567"));
    }

    @Test
    public void telValidator_CorrectPhoneWith10Numbers_ReturnsTrue() {
        assertTrue(TelValidator.isValidTelNumber("0622222222"));
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
