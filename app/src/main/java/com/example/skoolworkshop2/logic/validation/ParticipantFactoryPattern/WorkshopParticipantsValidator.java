package com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;


import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.ParticipantsValidatorInterface;

public class WorkshopParticipantsValidator implements ParticipantsValidatorInterface {

    private String LOG_TAG = getClass().getSimpleName();
    public boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidMaxParticipant(CharSequence amountOfParticipants, CharSequence amountWorkshopRounds) {
        //TODO validation werkt nog niet goed.
        double participants = 0.0;
        double workshopRounds = 0.0;

        if(amountOfParticipants != null){
            try {
                participants = Double.parseDouble(amountOfParticipants.toString());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        if(amountWorkshopRounds != null){
            try {
                workshopRounds = Double.parseDouble(amountWorkshopRounds.toString());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(participants + "---------------");
        System.out.println(workshopRounds + "---------------");

        if (amountOfParticipants.length() != 0 && participants > 25.0){
            System.out.println("in first if");
            double cal = (participants / 25.0);
            double finalValue = Math.round( cal * 100.0 ) / 100.0;
            if(finalValue <= workshopRounds){
                return true;
            } else {
                return false;
            }


        } else if(amountOfParticipants.length() != 0) {
            System.out.println("in second if");
            return participants > 0 && participants <= 25;
        } else {
            return false;
        }
    }
}