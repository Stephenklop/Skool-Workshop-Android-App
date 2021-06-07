package com.example.skoolworkshop2.validationsTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;

import org.junit.Test;

public class CultureDayParticipantsValidatorTest {
    @Test
    public void culturedayParticipantValidator_CorrectAmountGiven_ReturnsTrue() {
        assertTrue(CultureDayParticipantsValidator.isValidMaxParticipant("20"));
    }

    @Test
    public void culturedayParticipantValidator_CorrectMinimumAmountGiven_ReturnsTrue() {
        assertTrue(CultureDayParticipantsValidator.isValidMaxParticipant("1"));
    }

    @Test
    public void culturedayParticipantValidator_CorrectMaximumAmountGiven_ReturnsTrue() {
        assertTrue(CultureDayParticipantsValidator.isValidMaxParticipant("100"));
    }

    @Test
    public void culturedayParticipantValidator_InvalidParticipantMaximumGiven_ReturnsFalse() {
        assertFalse(CultureDayParticipantsValidator.isValidMaxParticipant("101"));
    }

    @Test
    public void culturedayParticipantValidator_InvalidParticipant130Given_ReturnsFalse() {
        assertFalse(CultureDayParticipantsValidator.isValidMaxParticipant("130"));
    }

    @Test
    public void culturedayParticipantValidator_InvalidParticipant0Given_ReturnsFalse() {
        assertFalse(CultureDayParticipantsValidator.isValidMaxParticipant("0"));
    }
}
