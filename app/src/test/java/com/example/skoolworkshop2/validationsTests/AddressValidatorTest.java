package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.AddressValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddressValidatorTest {
    @Test
    public void addressValidator_CorrectAddressGiven1_ReturnsTrue() {
        assertTrue(AddressValidator.isValidAdressValidator("4"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven2_ReturnsTrue() {
        assertTrue(AddressValidator.isValidAdressValidator("44"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven3_ReturnsTrue() {
        assertTrue(AddressValidator.isValidAdressValidator("444"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven1withLetter_ReturnsTrue() {
        assertTrue(AddressValidator.isValidAdressValidator("4a"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven2withLetter_ReturnsTrue() {
        assertTrue(AddressValidator.isValidAdressValidator("44a"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven3withLetter_ReturnsTrue() {
        assertTrue(AddressValidator.isValidAdressValidator("444a"));
    }
    @Test
    public void addressValidator_WrongAddressGiven1with2Letter_ReturnsFalse() {
        assertFalse(AddressValidator.isValidAdressValidator("4aa"));
    }
    @Test
    public void addressValidator_WrongAddressGiven2with2Letter_ReturnsFalse() {
        assertFalse(AddressValidator.isValidAdressValidator("44aa"));
    }
    @Test
    public void addressValidator_WrongAddressGiven3with2Letter_ReturnsFalse() {
        assertFalse(AddressValidator.isValidAdressValidator("444aa"));
    }
    @Test
    public void addressValidator_NoAdressGiven_ReturnsFalse() {
        assertFalse(AddressValidator.isValidAdressValidator(""));
    }


}
