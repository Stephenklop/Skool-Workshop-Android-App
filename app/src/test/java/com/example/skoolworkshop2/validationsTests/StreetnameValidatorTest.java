package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.addressInfoValidators.PlaceValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.StreetnameValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StreetnameValidatorTest {
    @Test
    public void streetnameValidator_CorrectStreetGiven_ReturnsTrue() {
        assertTrue(StreetnameValidator.isValidStreetname("Haverblok"));
    }
    @Test
    public void streetnameValidator_CorrectStreetGivenWithSpace_ReturnsTrue() {
        assertTrue(StreetnameValidator.isValidStreetname("Haver blok"));
    }
    @Test
    public void streetnameValidator_CorrectStreetGivenWithSpaces_ReturnsTrue() {
        assertTrue(StreetnameValidator.isValidStreetname("Hav er blok"));
    }
    @Test
    public void streetnameValidator_CorrectPlaceStreetWithSymbool_ReturnsTrue() {
        assertTrue(StreetnameValidator.isValidStreetname("Hav'er-blok"));
    }

    @Test
    public void streetnameValidator_WrongStreetGivenNumberAfter_ReturnsFalse() {
        assertFalse(StreetnameValidator.isValidStreetname("Haverblok1"));
    }
    @Test
    public void streetnameValidator_WrongStreetGivenNumber_ReturnsFalse() {
        assertFalse(StreetnameValidator.isValidStreetname("1"));
    }
    @Test
    public void streetnameValidator_WrongStreetGivenEmptyReturnsFalse() {
        assertFalse(StreetnameValidator.isValidStreetname(""));
    }
}
