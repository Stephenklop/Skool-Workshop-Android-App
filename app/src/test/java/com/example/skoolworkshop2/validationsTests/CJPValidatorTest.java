package com.example.skoolworkshop2.validationsTests;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.CJPValidator;

public class CJPValidatorTest {

    @Test
    public void cjpValidator_CorrectCJPGiven_ReturnsTrue() {
        assertTrue(CJPValidator.isValidCJP("7234"));
    }

    @Test
    public void cjpValidator_CorrectMinimumCJPGiven_ReturnsTrue() {
        assertTrue(CJPValidator.isValidCJP("1111"));
    }

    @Test
    public void cjpValidator_CorrectMaximumCJPGiven_ReturnsTrue() {
        assertTrue(CJPValidator.isValidCJP("9999"));
    }
    @Test
    public void cjpValidator_CorrectEmpty_ReturnsTrue() {
        assertTrue(CJPValidator.isValidCJP(""));
    }

    @Test
    public void cjpValidator_InvalidMaximumCJPGiven_ReturnsFalse() {
        assertFalse(CJPValidator.isValidCJP("99991"));
    }

    @Test
    public void cjpValidator_InvalidMinimumCJPGiven_ReturnsFalse() {
        assertFalse(CJPValidator.isValidCJP("9"));
    }

    @Test
    public void cjpValidator_ZeroCJPGiven_ReturnsFalse() {
        assertFalse(CJPValidator.isValidCJP("0"));
    }
}
