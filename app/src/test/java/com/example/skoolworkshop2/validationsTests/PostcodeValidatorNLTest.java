package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorNL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
public class PostcodeValidatorNLTest {
    @Test
    public void postcodeValidatorNL_CorrectInfoGiven_ReturnsTrue() {
        assertTrue(PostcodeValidatorNL.isValidPostcode("2020RB"));
    }

    @Test
    public void postcodeValidatorNL_CorrectInfoWithSpace_ReturnsTrue() {
        assertTrue(PostcodeValidatorNL.isValidPostcode("2020 RB"));
    }

    @Test
    public void postcodeValidatorNL_CorrectInfoGivenWithSmallLetter_ReturnsTrue() {
        assertTrue(PostcodeValidatorNL.isValidPostcode("2020rb"));
    }

    @Test
    public void postcodeValidatorNL_InvalidInfoNoLetter_ReturnsFalse() {
        assertFalse(PostcodeValidatorNL.isValidPostcode("1011"));
    }

    @Test
    public void postcodeValidatorNL_InvalidInfoNoNumber_ReturnsFalse() {
        assertFalse(PostcodeValidatorNL.isValidPostcode("RB"));
    }

    @Test
    public void postcodeValidatorNL_InvalidInfoOnlyLetter_ReturnsFalse() {
        assertFalse(PostcodeValidatorNL.isValidPostcode("RBeB"));
    }
}
