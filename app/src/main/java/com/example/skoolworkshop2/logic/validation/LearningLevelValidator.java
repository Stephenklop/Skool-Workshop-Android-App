package com.example.skoolworkshop2.logic.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

public class LearningLevelValidator{


    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidLearningLevels(CharSequence learns) {

        return learns != null || learns.toString().equals("");
    }

}
