package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.addressInfoValidators.HouseNumberValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HouseNumberValidatorTest {
    @Test
    public void addressValidator_CorrectAddressGiven1_ReturnsTrue() {
        assertTrue(HouseNumberValidator.isValidAdressValidator("4"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven2_ReturnsTrue() {
        assertTrue(HouseNumberValidator.isValidAdressValidator("44"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven3_ReturnsTrue() {
        assertTrue(HouseNumberValidator.isValidAdressValidator("444"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven1withLetter_ReturnsTrue() {
        assertTrue(HouseNumberValidator.isValidAdressValidator("4a"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven2withLetter_ReturnsTrue() {
        assertTrue(HouseNumberValidator.isValidAdressValidator("44a"));
    }
    @Test
    public void addressValidator_CorrectAddressGiven3withLetter_ReturnsTrue() {
        assertTrue(HouseNumberValidator.isValidAdressValidator("444a"));
    }
    @Test
    public void addressValidator_WrongAddressGiven1with2Letter_ReturnsFalse() {
        assertFalse(HouseNumberValidator.isValidAdressValidator("4aa"));
    }
    @Test
    public void addressValidator_WrongAddressGiven2with2Letter_ReturnsFalse() {
        assertFalse(HouseNumberValidator.isValidAdressValidator("44aa"));
    }
    @Test
    public void addressValidator_WrongAddressGiven3with2Letter_ReturnsFalse() {
        assertFalse(HouseNumberValidator.isValidAdressValidator("444aa"));
    }
    @Test
    public void addressValidator_NoAdressGiven_ReturnsFalse() {
        assertFalse(HouseNumberValidator.isValidAdressValidator(""));
    }


}
