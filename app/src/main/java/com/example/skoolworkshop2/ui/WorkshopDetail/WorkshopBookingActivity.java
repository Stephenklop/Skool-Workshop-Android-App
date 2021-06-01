package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.ui.MainActivity;

public class WorkshopBookingActivity extends FragmentActivity implements View.OnClickListener {
    private Button mSendBn;
    private ImageButton mBackButton;
    private DateValidation dateValidation = new DateValidation();
    private LearningLevelValidator learningLevelValidator = new LearningLevelValidator();
    private MinuteValidator minuteValidator = new MinuteValidator();
    private ParticipantsValidator participantsValidator = new ParticipantsValidator();
    private RoundsValidator roundsValidator = new RoundsValidator();
    private EditText mDateEditText;
    private EditText mLevelEditText;
    private EditText mMinuteEditText;
    private EditText mParticipantsEditText;
    private EditText mRoundsEditText;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_booking);

        //assign id
        mSendBn = findViewById(R.id.activity_workshop_booking_btn_book);
        mBackButton = findViewById(R.id.activity_workshop_booking_btn_back);
//        mDateEditText = findViewById(R.id.activity_workshop_booking_et_date);
        mLevelEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_level);
        mMinuteEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_mins);
//        mParticipantsEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_amount);
        mRoundsEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_rounds);

        //Use validator
//        mDateEditText.addTextChangedListener(dateValidation);
        mLevelEditText.addTextChangedListener(learningLevelValidator);
        mMinuteEditText.addTextChangedListener(minuteValidator);
//        mParticipantsEditText.addTextChangedListener(participantsValidator);
        mRoundsEditText.addTextChangedListener(roundsValidator);


        mSendBn.setText("Verzenden");



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

