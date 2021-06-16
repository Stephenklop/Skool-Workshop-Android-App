package com.example.skoolworkshop2.logic.validation;

public class AddressValidator {
    public static boolean mIsValid = false;

    public static boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidAddress(String address) {
        String[] splitAddress = address.split(" ");

        return splitAddress[splitAddress.length - 1].matches("\\d+\\w?");
    }
}
