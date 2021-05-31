package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class LearningLevelValidator implements TextWatcher{

    private String LOG_TAG = getClass().getSimpleName();
    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidLearningLevels(CharSequence learns) {
        return learns != null && learns != "";
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
            mIsValid = isValidLearningLevels(s);
            if (mIsValid) {
                Log.d(LOG_TAG, "afterTextChanged: learning level is valid");
            } else {
                Log.d(LOG_TAG, "afterTextChanged: learning level is invalid");
            }
        }
    }
}
