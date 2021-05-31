package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;


import java.util.regex.Pattern;

public class ParticipantsValidator implements TextWatcher{

    private String LOG_TAG = getClass().getSimpleName();
    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidMaxParticipant(CharSequence participants) {
        int participant = Integer.valueOf(participants.toString());
        return participant >0;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() != 0 ) {
            mIsValid = isValidMaxParticipant(s);
            if (mIsValid == true) {
                Log.d(LOG_TAG, "afterTextChanged: phone number is valid");
            } else {
                Log.d(LOG_TAG, "afterTextChanged: phone number is invalid");
            }
        }
    }
}