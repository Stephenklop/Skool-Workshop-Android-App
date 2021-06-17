package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class TelValidator implements TextWatcher {

    private String LOG_TAG = getClass().getSimpleName();

    public static final Pattern TELPATTERN = Pattern.compile(
            "^((\\+|00(\\s|\\s?\\-\\s?)?)31(\\s|\\s?\\-\\s?)?(\\(0\\)[\\-\\s]?)?|0)[1-9]((\\s|\\s?\\-\\s?)?[0-9])((\\s|\\s?-\\s?)?[0-9])((\\s|\\s?-\\s?)?[0-9])\\s?[0-9]\\s?[0-9]\\s?[0-9]\\s?[0-9]\\s?[0-9]$");
    public static final Pattern TELPATTERNBE = Pattern.compile(
            "^((\\+|00(\\s|\\s?\\-\\s?)?)32(\\s|\\s?\\-\\s?)?(\\(0\\)[\\-\\s]?)?|0)[1-9]((\\s|\\s?\\-\\s?)?[0-9])((\\s|\\s?-\\s?)?[0-9])((\\s|\\s?-\\s?)?[0-9])\\s?[0-9]\\s?[0-9]\\s?[0-9]\\s?[0-9]\\s?[0-9]$");

    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidTelNumber(CharSequence tel) {

        if(tel != null){
            if(TELPATTERN.matcher(tel).matches() || TELPATTERNBE.matcher(tel).matches()){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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
