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


    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");


    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidDate(CharSequence date) {
        DateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
        date1.setLenient(false);
        try {
            date1.parse(date.toString());
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
