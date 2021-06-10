package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.NameValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NameValidatorTest {
    @Test
    public void nameValidator_CorrectNameGiven_ReturnsTrue() {
        assertTrue(NameValidator.isValidNameValidator("Pieter"));
    }

    @Test
    public void nameValidator_CorrectNameGivenWithAllowedSymbol_ReturnsTrue() {
        assertTrue(NameValidator.isValidNameValidator("O'Neal"));
    }

    @Test
    public void nameValidator_CorrectNameGivenWithMultipleAllowedSymbol_ReturnsTrue() {
        assertTrue(NameValidator.isValidNameValidator("O'Neal-o"));
    }

    @Test
    public void nameValidator_IncorrectNameWrongSymbol_ReturnsFalse() {
        assertFalse(NameValidator.isValidNameValidator("Ar@a"));
    }

    @Test
    public void nameValidator_IncorrectNameEmpty_ReturnsFalse() {
        assertFalse(NameValidator.isValidNameValidator(""));
    }


    @Test
    public void nameValidator_NullName_ReturnsFalse() {
        assertFalse(NameValidator.isValidNameValidator(null));
    }

}
