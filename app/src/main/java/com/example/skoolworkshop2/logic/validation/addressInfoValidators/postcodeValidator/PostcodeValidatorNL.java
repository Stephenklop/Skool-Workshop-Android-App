package com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostcodeValidatorNL implements PostcodeValidator{


    @Override
    public boolean isValid() {
        return false;
    }


    public static boolean isValidPostcode(CharSequence postcode) {
        if(postcode != null && !postcode.toString().equals("")){
            String regx = "^[1-9][0-9]{3} ?(?!sa|sd|ss)[a-z]{2}$";
            Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(postcode.toString());
            return matcher.find();
        } else {
            return false;
        }
    }
}
