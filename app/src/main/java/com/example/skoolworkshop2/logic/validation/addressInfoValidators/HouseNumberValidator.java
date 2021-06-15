package com.example.skoolworkshop2.logic.validation.addressInfoValidators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HouseNumberValidator {

    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidAdressValidator(CharSequence learns) {
        if(learns != null && !learns.toString().equals("")) {
            String regx = "^\\d{1,3}[a-zA-Z]{0,1}$";
            Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(learns.toString());
            return matcher.find();
        } else {
            return false;
        }


    }

}
