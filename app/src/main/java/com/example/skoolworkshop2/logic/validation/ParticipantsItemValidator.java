package com.example.skoolworkshop2.logic.validation;

public class ParticipantsItemValidator {

    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidParticipantsItemValidator(CharSequence rounds) {
        if (rounds.length() != 0) {
            int round = Integer.valueOf(rounds.toString());
            return round >= 0;
        } else {
            return false;
        }
    }
}

