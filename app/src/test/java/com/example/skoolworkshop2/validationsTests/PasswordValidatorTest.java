package com.example.skoolworkshop2.validationsTests;



import com.example.skoolworkshop2.logic.validation.PasswordValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordValidatorTest {

    @Test
    public void passwordValidator_CorrectPasswordGiven_ReturnsTrue() {
        assertTrue(PasswordValidator.isValidPassword("22-Mayonaises"));
    }

    @Test
    public void passwordValidator_CorrectMultipleSpecialCharactersGiven_ReturnsTrue() {
        assertTrue(PasswordValidator.isValidPassword("22-Mayonaise!"));
    }

    @Test
    public void passwordValidator_CorrectMultipleUppercaseCharactersGiven_ReturnsTrue() {
        assertTrue(PasswordValidator.isValidPassword("22-MayonAISe!-"));
    }

    @Test
    public void passwordValidator_CorrectMultipleNumbersGiven_ReturnsFalse() {
        assertTrue(PasswordValidator.isValidPassword("22-Mayonaises12"));
    }

    @Test
    public void passwordValidator_InvalidPasswordNoUpperCaseGiven_ReturnsFalse() {
        assertFalse(PasswordValidator.isValidPassword("22-mayonaise"));
    }

    @Test
    public void passwordValidator_InvalidPasswordNoSpecialCharactersGiven_ReturnsFalse() {
        assertFalse(PasswordValidator.isValidPassword("22Mayonaise"));
    }

    @Test
    public void passwordValidator_InvalidPasswordNoNumbersGiven_ReturnsFalse() {
        assertFalse(PasswordValidator.isValidPassword("-Mayonaise--"));
    }

    @Test
    public void passwordValidator_InvalidPasswordLengthShorterThan12_ReturnsFalse() {
        assertFalse(PasswordValidator.isValidPassword("-22Mayo"));
    }

    @Test
    public void passwordValidator_EmptyString_ReturnsFalse() {
        assertFalse(PasswordValidator.isValidPassword(""));
    }

    @Test
    public void passwordValidator_NullEmail_ReturnsFalse() {
        assertFalse(PasswordValidator.isValidPassword(null));
    }

}
