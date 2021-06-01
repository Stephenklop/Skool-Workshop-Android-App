package com.example.skoolworkshop2.validationsTests;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.CJPValidator;

public class CJPValidatorTest {

    @Test
    public void cjpValidator_CorrectCJPGiven_ReturnsTrue() {
        assertTrue(CJPValidator.isValidCJP("72340000"));
    }

    @Test
    public void cjpValidator_CorrectMinimumCJPGiven_ReturnsTrue() {
        assertTrue(CJPValidator.isValidCJP("70000000"));
    }

    @Test
    public void cjpValidator_CorrectMaximumCJPGiven_ReturnsTrue() {
        assertTrue(CJPValidator.isValidCJP("80000000"));
    }

    @Test
    public void cjpValidator_InvalidMaximumCJPGiven_ReturnsTrue() {
        assertFalse(CJPValidator.isValidCJP("80000001"));
    }

    @Test
    public void cjpValidator_InvalidMinimumCJPGiven_ReturnsTrue() {
        assertFalse(CJPValidator.isValidCJP("69999999"));
    }

    @Test
    public void cjpValidator_ZeroCJPGiven_ReturnsTrue() {
        assertFalse(CJPValidator.isValidCJP("0"));
    }
}
