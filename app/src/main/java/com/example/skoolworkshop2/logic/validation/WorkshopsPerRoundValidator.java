package com.example.skoolworkshop2.logic.validation;

public class WorkshopsPerRoundValidator {
    private boolean mIsValid = false;

    public boolean isValid() {
        return ismIsValid();
    }

    public static boolean isValidWorkshopsPerRound(CharSequence rounds) {
        if (rounds.length() != 0) {
            int round = Integer.valueOf(rounds.toString());
            return round > 0;
        } else {
            return false;
        }
    }

    public boolean ismIsValid() {
        return mIsValid;
    }

    public void setmIsValid(boolean mIsValid) {
        this.mIsValid = mIsValid;
    }
}