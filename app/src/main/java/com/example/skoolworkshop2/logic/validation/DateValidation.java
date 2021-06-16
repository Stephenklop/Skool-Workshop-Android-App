package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DateValidation {

    public boolean mIsValid = false;


    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static String regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";


    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidDate(CharSequence date) {
//        DateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
//        date1.setLenient(false);
        if(date == null || !date.toString().matches(regex)) {
            return false;
        }
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date.toString());
            return true;
        } catch (ParseException ex){
            return false;
        }

    }
}