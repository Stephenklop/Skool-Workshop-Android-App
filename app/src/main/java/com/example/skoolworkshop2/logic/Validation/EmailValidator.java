package com.example.skoolworkshop2.logic.Validation;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Pattern;

public class EmailValidator implements TextWatcher {

    public static final Pattern EMAILPATTERN = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");

    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidEmail(CharSequence email) {
    return email != null && EMAILPATTERN.matcher(email).matches();
    }

     @Override
     final public void afterTextChanged(Editable editableText) {
        mIsValid = isValidEmail(editableText);
     }

     @Override
     final public void beforeTextChanged(CharSequence s, int start, int count, int after){

     }

     @Override
     final public void onTextChanged(CharSequence s, int start, int before, int count){
     }
}