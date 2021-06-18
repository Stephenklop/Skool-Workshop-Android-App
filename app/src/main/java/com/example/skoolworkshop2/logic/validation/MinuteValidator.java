package com.example.skoolworkshop2.logic.validation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class MinuteValidator{

    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidMinute(CharSequence minutes, CharSequence workshopRounds) {
        if(minutes.length() != 0){
            double amountOfMinutes = Double.valueOf(minutes.toString());
            double amountOfWorkshopRounds = Double.valueOf(workshopRounds.toString());
            double totaal = (amountOfMinutes * 2.5 * amountOfWorkshopRounds);
            return totaal >= 150;
        } else {
            return false;
        }

    }

}