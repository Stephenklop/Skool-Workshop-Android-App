package com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern;

public interface ParticipantsValidatorInterface {
    boolean isValid();

    static boolean isValidMaxParticipant(CharSequence participants) {
        return false;
    }
}
