package com.example.skoolworkshop2.validationsTests;

import com.example.skoolworkshop2.logic.validation.ParticipantsValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WorkshopParticipantsValidatorTest {

    @Test
    public void participantValidator_CorrectAmountGiven_ReturnsTrue() {
        assertTrue(ParticipantsValidator.isValidMaxParticipant("20"));
    }

    @Test
    public void participantValidator_CorrectMinimumAmountGiven_ReturnsTrue() {
        assertTrue(ParticipantsValidator.isValidMaxParticipant("1"));
    }

    @Test
    public void participantValidator_CorrectMaximumAmountGiven_ReturnsTrue() {
        assertTrue(ParticipantsValidator.isValidMaxParticipant("25"));
    }

    @Test
    public void participantValidator_InvalidParticipantMaximumGiven_ReturnsFalse() {
        assertFalse(ParticipantsValidator.isValidMaxParticipant("26"));
    }

    @Test
    public void minuteValidator_InvalidParticipant130Given_ReturnsFalse() {
        assertFalse(ParticipantsValidator.isValidMaxParticipant("130"));
    }

    @Test
    public void minuteValidator_InvalidParticipant0Given_ReturnsFalse() {
        assertFalse(ParticipantsValidator.isValidMaxParticipant("0"));
    }
}
