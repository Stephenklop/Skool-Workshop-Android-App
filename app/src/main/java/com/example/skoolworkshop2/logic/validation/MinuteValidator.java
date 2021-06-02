package com.example.skoolworkshop2.logic.validation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class MinuteValidator{

    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidMinute(CharSequence minutes) {
        if(minutes.length() != 0){
            int minute = Integer.valueOf(minutes.toString());
            return minute <= 120 && minute >= 45;
        } else {
            return false;
        }

    }
}
