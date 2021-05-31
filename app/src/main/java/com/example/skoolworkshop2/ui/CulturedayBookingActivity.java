package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;

public class CulturedayBookingActivity extends FragmentActivity implements View.OnClickListener {

    private String LOG_TAG = getClass().getSimpleName();
    private ImageButton mBackButton;
    private EditText mPersonEditText;
    private EditText mDateEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_question);

        // Setting up IDS
        mBackButton = findViewById(R.id.activity_cultureday_question_btn_back);
        mPersonEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_amount);
//        mDateEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_date);
        mPhoneEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_phone);
        mEmailEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_email);

    }

    @Override
    public void onClick(View v) {

    }
}
