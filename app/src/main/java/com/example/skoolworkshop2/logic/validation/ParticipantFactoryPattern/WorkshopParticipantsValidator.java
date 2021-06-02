package com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;


import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.ParticipantsValidatorInterface;

public class WorkshopParticipantsValidator implements ParticipantsValidatorInterface {

    private String LOG_TAG = getClass().getSimpleName();
    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidMaxParticipant(CharSequence participants) {
        if (participants.length() != 0){
            int participant = Integer.valueOf(participants.toString());
            return participant > 0 && participant <= 25;
        } else {
            return false;
        }
    }
}