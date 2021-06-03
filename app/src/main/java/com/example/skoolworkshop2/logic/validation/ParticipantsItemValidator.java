package com.example.skoolworkshop2.logic.validation;

public class ParticipantsItemValidator {

    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidParticipantsItemValidator(CharSequence items, int maxParticipants) {
        if (items.length() != 0){
            int item = Integer.valueOf(items.toString());
            return item <= maxParticipants && item >= 0;
        } else {
            return false;
        }
    }
}

