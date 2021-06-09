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
    public void postcodeValidatorBE_CorrectInfoWithSpace_ReturnsTrue() {
        assertTrue(PostcodeValidatorNL.isValidPostcode("2020 RB"));
    }

    @Test
    public void postcodeValidatorBE_CorrectInfoGivenWithSmallLetter_ReturnsTrue() {
        assertTrue(PostcodeValidatorNL.isValidPostcode("2020rb"));
    }

    @Test
    public void postcodeValidatorBE_InvalidInfoNoLetter_ReturnsFalse() {
        assertFalse(PostcodeValidatorNL.isValidPostcode("1011"));
    }

    @Test
    public void postcodeValidatorBE_InvalidInfoNoNumber_ReturnsFalse() {
        assertFalse(PostcodeValidatorNL.isValidPostcode("RB"));
    }

    @Test
    public void postcodeValidatorBE_InvalidInfoOnlyLetter_ReturnsFalse() {
        assertFalse(PostcodeValidatorNL.isValidPostcode("RBeB"));
    }
}
