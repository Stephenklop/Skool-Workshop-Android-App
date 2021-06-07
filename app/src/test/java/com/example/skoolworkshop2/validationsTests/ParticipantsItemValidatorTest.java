package com.example.skoolworkshop2.validationsTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantsItemValidator;

import org.junit.Test;

public class ParticipantsItemValidatorTest {
    @Test
    public void participantsItemValidor_CorrectAmountGiven_ReturnsTrue() {
        assertTrue(ParticipantsItemValidator.isValidParticipantsItemValidator("19", 20));
    }

    @Test
    public void participantsItemValidor_CorrectMinimumAmountGiven_ReturnsTrue() {
        assertTrue(ParticipantsItemValidator.isValidParticipantsItemValidator("0",1));
    }

    @Test
    public void participantsItemValidor_CorrectMaximumAmountGiven_ReturnsTrue() {
        assertTrue(ParticipantsItemValidator.isValidParticipantsItemValidator("100", 100));
    }

    @Test
    public void participantsItemValidor_InvalidParticipantItemMaximumGiven_ReturnsFalse() {
        assertFalse(ParticipantsItemValidator.isValidParticipantsItemValidator("101", 100));
    }

    @Test
    public void participantsItemValidor_InvalidParticipantItem130Given_ReturnsFalse() {
        assertFalse(ParticipantsItemValidator.isValidParticipantsItemValidator("130", 20));
    }

    @Test
    public void participantsItemValidor_InvalidParticipant0Given_ReturnsTrue() {
        assertTrue(ParticipantsItemValidator.isValidParticipantsItemValidator("0", 10));
    }
}
