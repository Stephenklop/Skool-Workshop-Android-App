package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorBE;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorNL;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostcodeValidatorBETest {
    @Test
    public void postcodeValidatorBE_CorrectInfoGiven_ReturnsTrue() {
        assertTrue(PostcodeValidatorBE.isValidPostcode("2020"));
    }

    @Test
    public void postcodeValidatorBE_CorrectInfoRandomNumber_ReturnsTrue() {
        assertTrue(PostcodeValidatorBE.isValidPostcode("1231"));
    }

    @Test
    public void postcodeValidatorBE_CorrectInfoGivenMax_ReturnsTrue() {
        assertTrue(PostcodeValidatorBE.isValidPostcode("9999"));
    }

    @Test
    public void postcodeValidatorBE_InvalidInfoLetter_ReturnsFalse() {
        assertFalse(PostcodeValidatorBE.isValidPostcode("1011BB"));
    }

    @Test
    public void postcodeValidatorBE_InvalidInfoNoNumber_ReturnsFalse() {
        assertFalse(PostcodeValidatorBE.isValidPostcode("RB"));
    }

    @Test
    public void postcodeValidatorBE_InvalidInfoOnlyLetter_ReturnsFalse() {
        assertFalse(PostcodeValidatorBE.isValidPostcode("RBeB"));
    }
}
