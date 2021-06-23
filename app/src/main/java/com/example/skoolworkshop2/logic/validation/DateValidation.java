package com.example.skoolworkshop2.logic.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateValidation {

    private boolean mIsValid = false;


    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static String regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";


    public boolean isValid() {
        return ismIsValid();
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
            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH) + 1;
            Date today = new Date();
            today.setMonth(month);
            return !simpleDateFormat.parse(date.toString()).before(today);
        } catch (ParseException ex){
            return false;
        }

    }

    public boolean ismIsValid() {
        return mIsValid;
    }

    public void setmIsValid(boolean mIsValid) {
        this.mIsValid = mIsValid;
    }
}