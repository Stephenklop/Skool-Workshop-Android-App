package com.example.skoolworkshop2.logic.validation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class EmailValidator{

    private String LOG_TAG = getClass().getSimpleName();

    public static final Pattern EMAILPATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");

    public static boolean mIsValid = false;

    public static boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidEmail(CharSequence email) {
    return email != null && EMAILPATTERN.matcher(email).matches();
    }
}