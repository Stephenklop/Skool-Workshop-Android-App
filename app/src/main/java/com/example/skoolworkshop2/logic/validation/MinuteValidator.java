package com.example.skoolworkshop2.logic.validation;

public class MinuteValidator{

    private boolean mIsValid = false;

    public boolean isValid() {
        return ismIsValid();
    }

    public static boolean isValidMinute(CharSequence minutes) {
        if(minutes.length() != 0){
            int minute = Integer.valueOf(minutes.toString());
            return minute <= 120 && minute >= 60;
        } else {
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