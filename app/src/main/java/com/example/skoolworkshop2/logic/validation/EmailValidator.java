package com.example.skoolworkshop2.logic.validation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class EmailValidator implements TextWatcher {

    private String LOG_TAG = getClass().getSimpleName();

    public static final Pattern EMAILPATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");

    private static boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidEmail(CharSequence email) {
    return email != null && EMAILPATTERN.matcher(email).matches();
    }

     @Override
     final public void afterTextChanged(Editable editableText) {
         if (editableText.length() != 0 ) {

             mIsValid = isValidEmail(editableText);
             if (mIsValid == true) {
                 Log.d(LOG_TAG, "afterTextChanged: email is valid");
             } else {
                 Log.d(LOG_TAG, "afterTextChanged: email is invalid");
             }
         }
     }

     @Override
     final public void beforeTextChanged(CharSequence s, int start, int count, int after){

     }

     @Override
     final public void onTextChanged(CharSequence s, int start, int before, int count){
     }
}