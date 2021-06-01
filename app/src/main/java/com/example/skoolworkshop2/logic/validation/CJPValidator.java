package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class CJPValidator implements TextWatcher {

    private String LOG_TAG = getClass().getSimpleName();
    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidCJP(CharSequence cjp) {
        int cjps = Integer.valueOf(cjp.toString());
        if(cjps >= 70000000 && cjps <= 80000000 ){
            return true;
        }
        return false;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mIsValid = isValidCJP(s);
        if(mIsValid){
            Log.d(LOG_TAG, "afterTextChanged: CJP is valid");
        } else {
            Log.d(LOG_TAG, "afterTextChanged: CJP is invalid");
        }
    }
}
