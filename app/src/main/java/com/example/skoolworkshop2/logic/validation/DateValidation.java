package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class DateValidation implements TextWatcher {

    private String LOG_TAG = getClass().getSimpleName();
    private boolean mIsValid = false;

    public static final Pattern DATEPATTERN = Pattern.compile("MM-dd-yyyy");


    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidDate(CharSequence date) {
        return date != null && DATEPATTERN.matcher(date).matches();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mIsValid = isValidDate(editable);
        if(mIsValid == true){
            Log.d(LOG_TAG, "afterTextChanged: date is valid");
        } else {
            Log.d(LOG_TAG, "afterTextChanged: date is invalid");
        }
    }
}
