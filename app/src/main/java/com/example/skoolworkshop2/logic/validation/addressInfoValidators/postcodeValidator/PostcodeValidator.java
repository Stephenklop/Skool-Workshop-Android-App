package com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator;

public interface PostcodeValidator {

    boolean isValid();
    void setmIsValid(boolean bol);

    static boolean isValidPostcode(CharSequence participants){
        return false;
    }
}