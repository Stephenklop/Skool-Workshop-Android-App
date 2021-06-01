package com.example.skoolworkshop2.logic.validation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class MinuteValidator implements TextWatcher{

    private String LOG_TAG = getClass().getSimpleName();
    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidMinute(CharSequence minutes) {
        int minute = Integer.valueOf(minutes.toString());
        return minute <= 120 && minute >= 45;

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
            mIsValid = isValidMinute(s);
            if (mIsValid == true) {
                Log.d(LOG_TAG, "afterTextChanged: minutes given is valid");
            } else {
                Log.d(LOG_TAG, "afterTextChanged: minutes given is invalid");
            }
        }
    }
}
