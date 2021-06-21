package com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern;

public class CultureDayParticipantsValidator implements ParticipantsValidatorInterface {

    private String LOG_TAG = getClass().getSimpleName();
    private boolean mIsValid = false;


    public boolean isValid() {
        return ismIsValid();
    }

    public static boolean isValidMaxParticipant(CharSequence participants) {
        if (participants.length() != 0){
            int participant = Integer.valueOf(participants.toString());
            return participant > 0 && participant <= 100;
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