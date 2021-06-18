package com.example.skoolworkshop2.logic.validation.addressInfoValidators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {
    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidUsername(CharSequence learns) {
        if(learns != null && !learns.toString().equals("")) {

            return true;
        } else {
            return false;
        }


    }
}
