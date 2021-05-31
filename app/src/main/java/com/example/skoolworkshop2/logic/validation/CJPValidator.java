package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Pattern;

public class CJPValidator implements TextWatcher {

    public static final Pattern CJPPATTERN = Pattern.compile(
            "(7000000[0-9]|700000[1-9][0-9]|70000[1-9][0-9]{2}|7000[1-9][0-9]{3}|700[1-9][0-9]{4}|70[1-9][0-9]{5}|7[1-9][0-9]{6}|80000000)");

    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidCJP(CharSequence cjp) {
        return cjp != null && CJPPATTERN.matcher(cjp).matches();
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
    }
}
