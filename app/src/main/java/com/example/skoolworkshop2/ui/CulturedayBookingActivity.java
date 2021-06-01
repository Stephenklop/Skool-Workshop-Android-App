package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;

public class CulturedayBookingActivity extends FragmentActivity implements View.OnClickListener {

    private String LOG_TAG = getClass().getSimpleName();
    private ImageButton mBackButton;
    private Button mSendBn;
    private EditText mPersonEditText;
    private EditText mDateEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_booking);

        // Setting up IDS
        mSendBn = findViewById(R.id.activity_cultureday_booking_btn_book);
        mBackButton = findViewById(R.id.activity_cultureday_booking_btn_back);

        mSendBn.setText("Boek nu");

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
