package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class CJPValidator {

    private String LOG_TAG = getClass().getSimpleName();
    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidCJP(CharSequence cjp) {
        if(!cjp.toString().equals("")){
            try{
                int cjps = Integer.valueOf(cjp.toString());
                if(cjps >= 70000000 && cjps <= 80000000 ){
                    return true;
                }
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}