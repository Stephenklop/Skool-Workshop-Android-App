package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.addressInfoValidators.AddressValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.PlaceValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlaceValidatorTest {
    @Test
    public void placeValidator_CorrectPlaceGiven_ReturnsTrue() {
        assertTrue(PlaceValidator.isValidPlace("Roosendaal"));
    }
    @Test
    public void placeValidator_CorrectPlaceGivenWithSpace_ReturnsTrue() {
        assertTrue(PlaceValidator.isValidPlace("Roos endaal"));
    }
    @Test
    public void placeValidator_CorrectPlaceGivenWithSpaces_ReturnsTrue() {
        assertTrue(PlaceValidator.isValidPlace("Roos en daal"));
    }
    @Test
    public void placeValidator_CorrectPlaceGivenWithSymbool_ReturnsTrue() {
        assertTrue(PlaceValidator.isValidPlace("Roos'en-daal"));
    }

    @Test
    public void placeValidator_WrongPlaceGivenNumber_ReturnsFalse() {
        assertFalse(PlaceValidator.isValidPlace("1"));
    }
    @Test
    public void placeValidator_WrongPlaceGivenEmptyReturnsFalse() {
        assertFalse(PlaceValidator.isValidPlace(""));
    }

}
