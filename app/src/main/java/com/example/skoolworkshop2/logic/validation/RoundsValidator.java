package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class RoundsValidator{
    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidWorkshopRounds(CharSequence rounds) {
        if (rounds.length() != 0) {
            int round = Integer.valueOf(rounds.toString());
            return round > 0;
        } else {
            return false;
        }
    }
}
