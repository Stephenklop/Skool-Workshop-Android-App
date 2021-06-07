package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class TelValidator implements TextWatcher {

    private String LOG_TAG = getClass().getSimpleName();

    public static final Pattern TELPATTERN = Pattern.compile(
            "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{1})(?: *x(\\d+))?\\s*$");

    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidTelNumber(CharSequence tel) {
        return tel != null && TELPATTERN.matcher(tel).matches();
    }

    @Override
    final public void afterTextChanged(Editable editableText) {
        mIsValid = isValidTelNumber(editableText);
        if(mIsValid == true){
            Log.d(LOG_TAG, "afterTextChanged: phone number is valid");
        } else {
            Log.d(LOG_TAG, "afterTextChanged: phone number is invalid");
        }
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after){

    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count){

    }
}
